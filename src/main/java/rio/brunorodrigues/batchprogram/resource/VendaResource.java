package rio.brunorodrigues.batchprogram.resource;

import org.springframework.data.repository.PagingAndSortingRepository;

import rio.brunorodrigues.batchprogram.model.Venda;


public interface VendaResource extends PagingAndSortingRepository<Venda, Integer> {

}
