package rio.brunorodrigues.batchprogram.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import rio.brunorodrigues.batchprogram.model.ItemVenda;
import rio.brunorodrigues.batchprogram.model.Status;
import rio.brunorodrigues.batchprogram.model.Venda;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface ItemRepository extends PagingAndSortingRepository<ItemVenda, Integer>{

    public ItemVenda findTop1ByVendaStatus(Status status);

    public List<ItemVenda> findByVenda(Venda venda);

    @Transactional
    @Query("select iv from ItemVenda iv left join iv.venda v where v.status = 'OK' and date(v.data) = :dataProcessamento")
    public List<ItemVenda> findCustomProcessedVenda(@Param("dataProcessamento") Date dataProcessamento);
}
				