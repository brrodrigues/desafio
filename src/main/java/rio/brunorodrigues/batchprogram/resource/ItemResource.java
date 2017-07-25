package rio.brunorodrigues.batchprogram.resource;

import org.springframework.data.repository.PagingAndSortingRepository;

import rio.brunorodrigues.batchprogram.model.ItemVenda;

public interface ItemResource extends PagingAndSortingRepository<ItemVenda, Integer>{

}
				