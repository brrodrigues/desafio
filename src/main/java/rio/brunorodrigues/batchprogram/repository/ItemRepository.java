package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import rio.brunorodrigues.batchprogram.model.ItemVenda;
import rio.brunorodrigues.batchprogram.model.Status;

public interface ItemRepository extends PagingAndSortingRepository<ItemVenda, Integer>{

    public ItemVenda findTop1ByVendaStatus(Status status);
}
				