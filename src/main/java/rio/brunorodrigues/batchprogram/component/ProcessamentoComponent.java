package rio.brunorodrigues.batchprogram.component;

import java.io.File;
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
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private Long currentLine;

    private static final String MASK_FILE = "%s_out_sale_%s.txt";

    AtomicInteger fileNameCount = new AtomicInteger();

    @Scheduled(fixedRateString = "${app.delay}")
    public void doPerformAction() {

        ItemVenda item = itemRepository.findTop1ByVendaStatus(Status.PENDENTE);

        if (checkItemVenda(item)) {
            return;
        }

        checkUserPath();

        int numFile = fileNameCount.get();

        String fileName = String.format(MASK_FILE, DateUtils.toYYYYMMDDDateFormat(), numFile);

        try {
            currentLine = Files.lines(Paths.get(fileName)).count();

            if (currentLine >= 10) {
                numFile = fileNameCount.incrementAndGet();
                fileName = String.format(MASK_FILE, DateUtils.toYYYYMMDDDateFormat(), numFile);
            }

            LOGGER.log(Level.INFO, "Opening file {0} to editing", fileName);

            FileOutputStream outputStream = new FileOutputStream(String.format(OUTPUT_PATH + File.separator + fileName), true);

            outputStream.write(writeLine(item));
            doFinishVenda(item.getVenda(), fileName);

        } catch (IOException ex) {
            Logger.getLogger(ProcessamentoComponent.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        processamento.setData(venda.getData());
        processamento.setLoja(venda.getLoja());
        processamento.setPdv(venda.getPdv());
        processamento.setNomeArquivo(fileName);
        venda.setStatus(Status.OK);

        processamentoRepository.save(processamento);

        vendaRepository.save(venda);

    }
}
