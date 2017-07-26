package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import rio.brunorodrigues.batchprogram.model.Processamento;

public interface ProcessamentoRepository extends PagingAndSortingRepository<Processamento, Integer> {
}
