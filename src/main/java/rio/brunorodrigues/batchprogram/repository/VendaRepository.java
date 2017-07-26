package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import rio.brunorodrigues.batchprogram.model.Status;
import rio.brunorodrigues.batchprogram.model.Venda;

import java.util.Collection;


public interface VendaRepository extends PagingAndSortingRepository<Venda, Integer> {

    public Collection<Venda> findByStatus(Status status);
}
