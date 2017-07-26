package rio.brunorodrigues.batchprogram.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rio.brunorodrigues.batchprogram.model.ItemVenda;
import rio.brunorodrigues.batchprogram.model.Processamento;
import rio.brunorodrigues.batchprogram.model.Status;
import rio.brunorodrigues.batchprogram.model.Venda;
import rio.brunorodrigues.batchprogram.repository.ItemRepository;
import rio.brunorodrigues.batchprogram.repository.ProcessamentoRepository;
import rio.brunorodrigues.batchprogram.repository.VendaRepository;
import rio.brunorodrigues.batchprogram.util.DateUtils;
import rio.brunorodrigues.batchprogram.util.ExporterUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProcessamentoComponent {

    private static final Logger LOGGER = Logger.getLogger(ProcessamentoComponent.class.getName());

    @Autowired private VendaRepository vendaRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private ProcessamentoRepository processamentoRepository;

    @Value("${app.output-file}")
    private String OUTPUT_PATH;

    @Value("${app.counter-file-line}")
    private Integer COUNTER_FILE;

    private static Integer currentLine = 0;

    private static final String MASK_FILE = "%s_out_sale_%s.txt";

    AtomicInteger fileNameCount = new AtomicInteger();

    @Scheduled(fixedDelay = 1000)
    public void doPerformAction() {

        ItemVenda item = itemRepository.findByVendaStatus(Status.NOK);


        synchronized (currentLine){

            if (currentLine <= COUNTER_FILE){
                try {
                    doIt(item, false);
                    currentLine++;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    currentLine = 0;
                    doIt(item, true);
                    currentLine++;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doIt(ItemVenda venda, boolean newFile) throws FileNotFoundException {

        int numFile = numFile = fileNameCount.get();

        if (newFile){
            numFile =fileNameCount.incrementAndGet();
        }

        String fileName = String.format(MASK_FILE, DateUtils.toYYYYMMDDDateFormat(), numFile);

        FileOutputStream outputStream = new FileOutputStream(String.format(OUTPUT_PATH + System.lineSeparator() + fileName));

        try {
            outputStream.write(doMountLineFile(venda));
            doFinishVenda(venda, fileName);

            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, " Fail at processing item {0}. Next item.", venda.toString() );
        }


    }


    private byte[] doMountLineFile(ItemVenda item){
        Venda venda = item.getVenda();

        StringBuilder tmp = new StringBuilder();

        tmp.append(ExporterUtils.toNumberStringZeroLength(venda.getId(), 10));
        tmp.append(ExporterUtils.toDateString(venda.getData(), ExporterUtils.FORMATTER.DDMMYYYY));
        tmp.append(ExporterUtils.toNumberStringZeroLength(venda.getLoja(), 3 ));
        tmp.append(ExporterUtils.toNumberStringZeroLength(venda.getPdv(), 2 ));
        tmp.append(ExporterUtils.toStringSpaceLength(item.getProduto(), 2 ));
        tmp.append(ExporterUtils.toNumberStringZeroLength(item.getPrecoUnitario(), 4, 2 ));
        tmp.append(ExporterUtils.toNumberStringZeroLength(item.getValorDesconto(), 4, 2 ));
        tmp.append(ExporterUtils.toNumberStringZeroLength(item.getValorTotal(), 4, 2 ));


        return  tmp.toString().getBytes();

    }

    public void doFinishVenda(ItemVenda item, String fileName) {
        Processamento processamento = new Processamento();

        Venda venda = item.getVenda();

        processamento.setData(item.getVenda().getData());
        processamento.setLoja(venda.getLoja());
        processamento.setPdv(venda.getPdv());
        processamento.setNomeArquivo(fileName);
        venda.setStatus(Status.OK);

        processamentoRepository.save(processamento);

        vendaRepository.save(venda);


    }
}
