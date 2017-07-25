package rio.brunorodrigues.batchprogram.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tb_item_venda")
public class ItemVenda implements Serializable {
	
	@EmbeddedId
	private ItemVendaPK id;
	
	@Column(name="produto")
	private String produto;
	@Column(name="preco_unitario")
	private BigDecimal precoUnitario;
	@Column(name="desconto")
	private BigDecimal valorDesconto;
	
	public ItemVendaPK getItemPK() {
		return id;
	}
	public void setItemPK(ItemVendaPK itemPK) {
		this.id = itemPK;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
	
	

	
}
