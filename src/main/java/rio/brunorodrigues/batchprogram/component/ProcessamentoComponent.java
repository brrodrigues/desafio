package rio.brunorodrigues.batchprogram.component;

import java.io.*;

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

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import rio.brunorodrigues.batchprogram.util.UserHomeUtils;

@Component
public class ProcessamentoComponent {

    private static final Logger LOGGER = Logger.getLogger(ProcessamentoComponent.class.getName());

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ProcessamentoRepository processamentoRepository;

    @Value("${app.output-file}")
    private String OUTPUT_PATH;

    private static final String MASK_FILE = "%s_out_sale_%s.txt";

    static AtomicInteger fileNameCount = new AtomicInteger(1);

    @Scheduled(fixedRateString = "${app.delay}")
    public void doPerformAction() {

        checkUserPath();

        Long currentLine = 0l;

        try {

            ItemVenda item = itemRepository.findTop1ByVendaStatus(Status.PENDENTE);

            if (item == null){
                LOGGER.log(Level.INFO, "No data to process...");
                return ;
            }

            List<ItemVenda> itens = itemRepository.findByVenda(item.getVenda());


            StringBuilder fileNames = new StringBuilder();

            String fileName = getFileName(fileNameCount.get());

            fileNames.append(fileName);

            if (Files.exists(Paths.get(fileName), LinkOption.NOFOLLOW_LINKS)){
                currentLine = Files.lines(Paths.get(fileName)).count();
            }
            LOGGER.log(Level.INFO, " file {0} contains {1} lines.", new Object [] {fileName, currentLine});

            FileOutputStream outputStream = createOrOpenFileName(fileNameCount, false);

            if (currentLine == 10) {
                LOGGER.log(Level.INFO, "The file achieved {0} lines. Creating a new file. ", 10);
                outputStream = createOrOpenFileName(fileNameCount, true);
            }

            for (int i = 0; i < itens.size(); i++) {

                outputStream.write(writeLine(itens.get(i)));
                currentLine++;

                LOGGER.log(Level.INFO,"current {0} result mod currentLine % 10 :{1} ", new Object [] {currentLine, currentLine % 10});
                if (currentLine % 10 == 0){
                    LOGGER.log(Level.INFO,"File achieved {0} lines. Creating a new file.", 10);
                    outputStream.close();//Closing current file
                    outputStream  = createOrOpenFileName(fileNameCount,true);

                    //The output sales could processed in more files, so I decide to write them all.
                    fileNames.append(",").append(getFileName(fileNameCount.get()));

                    currentLine = 0L;
                }

            }

            outputStream.close();

            doFinishVenda(item.getVenda(), fileNames.toString());


        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    private String getFileName(int i) {
        String maskedFileName = String.format(MASK_FILE, DateUtils.toYYYYMMDDDateFormat(), i);
        String fileName= String.format(OUTPUT_PATH + File.separator + maskedFileName);
        return fileName;
    }

    private FileOutputStream createOrOpenFileName(AtomicInteger fileNameCount, boolean newFile) throws FileNotFoundException {

        int count= fileNameCount.get();

        if (newFile){
            count = fileNameCount.incrementAndGet();
        }


        String fileName = getFileName(count);

        LOGGER.log(Level.INFO, "Opening file {0} to editing", fileName);

        FileOutputStream outputStream = new FileOutputStream(fileName, true);//Creating a new file
        return outputStream;
    }

    private boolean checkItemVenda(ItemVenda item) {
        if (item == null) {
            LOGGER.log(Level.INFO, "It does not exist output item");
            return true;
        }
        return false;
    }

    private void checkUserPath() {
        if (OUTPUT_PATH == null || OUTPUT_PATH.equals("")) {
            try {
                OUTPUT_PATH = UserHomeUtils.getPath();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Closing application...", ex);
                System.exit(0);
            }
        }
    }

    private byte[] writeLine(ItemVenda item) {
        Venda venda = item.getVenda();

        StringBuilder tmp = new StringBuilder();

        tmp.append(ExporterUtils.doLPADZero(venda.getId(), 10));
        tmp.append(ExporterUtils.toDateString(venda.getData(), ExporterUtils.FORMATTER.DDMMYYYY));
        tmp.append(ExporterUtils.doLPADZero(venda.getLoja(), 4));
        tmp.append(ExporterUtils.doLPADZero(venda.getPdv(), 3));
        tmp.append(ExporterUtils.doRPAD(item.getProduto(), 10));
        tmp.append(ExporterUtils.doLPADZero(item.getPrecoUnitario(), 5, 2));
        tmp.append(ExporterUtils.doLPADZero(item.getValorDesconto(), 5, 2));
        tmp.append(ExporterUtils.doLPADZero(item.getValorTotal(), 5, 2));
        tmp.append("\n");

        return tmp.toString().getBytes();

    }

    public void doFinishVenda(Venda venda, String fileName) {
        Processamento processamento = new Processamento();

        processamento.setData(new Date());
        processamento.setLoja(venda.getLoja());
        processamento.setPdv(venda.getPdv());
        processamento.setNomeArquivo(fileName);
        processamento.setStatus(Status.OK);
        venda.setStatus(Status.OK);

        processamentoRepository.save(processamento);

        vendaRepository.save(venda);

    }
}
