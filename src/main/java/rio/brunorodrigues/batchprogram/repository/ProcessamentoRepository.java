package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import rio.brunorodrigues.batchprogram.model.Processamento;
import rio.brunorodrigues.batchprogram.model.Venda;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface ProcessamentoRepository extends PagingAndSortingRepository<Processamento, Integer> {

    public List<Processamento> findByDataOrderById(Date data);


}
