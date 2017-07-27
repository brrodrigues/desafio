package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.repository.query.Param;
import rio.brunorodrigues.batchprogram.model.Status;
import rio.brunorodrigues.batchprogram.model.Venda;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;


public interface VendaRepository extends PagingAndSortingRepository<Venda, Integer> {

    public Collection<Venda> findByStatus(Status status);

    public Collection<Venda> findById(Integer id);

}
