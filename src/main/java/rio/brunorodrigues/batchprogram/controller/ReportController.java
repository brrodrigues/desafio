package rio.brunorodrigues.batchprogram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rio.brunorodrigues.batchprogram.dto.ReportDTO;
import rio.brunorodrigues.batchprogram.form.ReportForm;
import rio.brunorodrigues.batchprogram.formatter.DateFormatter;
import rio.brunorodrigues.batchprogram.model.ItemVenda;
import rio.brunorodrigues.batchprogram.model.Processamento;
import rio.brunorodrigues.batchprogram.model.Venda;
import rio.brunorodrigues.batchprogram.repository.ItemRepository;
import rio.brunorodrigues.batchprogram.repository.ProcessamentoRepository;
import rio.brunorodrigues.batchprogram.repository.VendaRepository;
import rio.brunorodrigues.batchprogram.util.DateUtils;
import rio.brunorodrigues.batchprogram.util.TypeConverter;
import rio.brunorodrigues.batchprogram.validator.ReportValidation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/reportController")
public class ReportController extends WebMvcConfigurerAdapter {


    private  static final Logger LOGGER = Logger.getLogger(ReportController.class.getName());

    @Autowired
    private ItemRepository repository;


    private List<ReportDTO> allItensProcessed = new ArrayList<>();

    private ReportForm form;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/reportController").setViewName("templates/report");
    }

    @Override
    public Validator getValidator() {
        return new ReportValidation();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateFormatter dateFormatter = new DateFormatter();
        registry.addFormatter(dateFormatter);
    }

    @ModelAttribute("form")
    public ReportForm getForm() {
        form = new ReportForm();
        LOGGER.log(Level.INFO, "retrieve form {0}", form.toString());
        return form;
    }

    @GetMapping
    public String viewReport(){
        return "report";
    }

    @PostMapping
    public String viewReport(final ReportForm form, Model model){

        List<ReportDTO> reportDTOS = new ArrayList<>();

        LOGGER.log(Level.INFO, "Report form {0} ", form.toString());

        Date data = null;

        try {
            data = DateUtils.toStringDate(form.getData());
            List<ItemVenda> vendas = repository.findCustomProcessedVenda(data);

            LOGGER.log(Level.INFO, " Total vendas {0} ", vendas.toString());

            if (vendas != null) {
                transformer(vendas);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }


        model.addAttribute("processedList", getAllItensProcessed());

        return "report";
    }

    private void transformer(List<ItemVenda> vendas) throws Exception {

        allItensProcessed = new ArrayList<>();

        for (ItemVenda processamento : vendas){
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setDataProcessamento(TypeConverter.toStringDateTime(processamento.getVenda().getData()));
            reportDTO.setValorDesconto(TypeConverter.toString(processamento.getValorDesconto()));
            reportDTO.setValorUnitario(TypeConverter.toString(processamento.getPrecoUnitario()));
            reportDTO.setProduto(processamento.getProduto());
            reportDTO.setLoja(TypeConverter.toString(processamento.getVenda().getLoja()));
            reportDTO.setPdv(TypeConverter.toString(processamento.getVenda().getPdv()));
            reportDTO.setValorTotal(TypeConverter.toString(processamento.getValorTotal()));
            allItensProcessed.add(reportDTO);

        }

    }

    public List<ReportDTO> getAllItensProcessed() {
        LOGGER.log(Level.INFO, " result {0} ", allItensProcessed);
        return allItensProcessed;
    }
}
