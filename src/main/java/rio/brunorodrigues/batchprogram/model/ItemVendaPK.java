package rio.brunorodrigues.batchprogram.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemVendaPK implements Serializable {

	@Column(name="id_venda")
	private Integer vendaId;
	@Column(name="id_item_venda")
	private Integer itemVendaId;
	
	public Integer getVendaId() {
		return vendaId;
	}
	public void setVendaId(Integer vendaId) {
		this.vendaId = vendaId;
	}
	public Integer getItemVendaId() {
		return itemVendaId;
	}
	public void setItemVendaId(Integer itemVendaId) {
		this.itemVendaId = itemVendaId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemVendaId == null) ? 0 : itemVendaId.hashCode());
		result = prime * result + ((vendaId == null) ? 0 : vendaId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVendaPK other = (ItemVendaPK) obj;
		if (itemVendaId == null) {
			if (other.itemVendaId != null)
				return false;
		} else if (!itemVendaId.equals(other.itemVendaId))
			return false;
		if (vendaId == null) {
			if (other.vendaId != null)
				return false;
		} else if (!vendaId.equals(other.vendaId))
			return false;
		return true;
	}

	
	
	
}
