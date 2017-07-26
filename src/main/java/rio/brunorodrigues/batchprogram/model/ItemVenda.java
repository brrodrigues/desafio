package rio.brunorodrigues.batchprogram.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name="tb_item_venda")
public class ItemVenda implements Serializable {
	
	@EmbeddedId
	private ItemVendaPK id;

	@JoinColumn(name = "id_venda", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Venda venda;

	@Column(name="produto")
	private String produto;
	@Column(name="preco_unitario")
	private BigDecimal precoUnitario;
	@Column(name="desconto")
	private BigDecimal valorDesconto;

    public ItemVendaPK getId() {
        return id;
    }

    public void setId(ItemVendaPK id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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

    public BigDecimal getValorTotal() {


        BigDecimal precoUnitarioNew = precoUnitario;
        if (precoUnitarioNew == null) {
            precoUnitarioNew = BigDecimal.ZERO;
        }
        BigDecimal valorDescontoNew = valorDesconto;

        if (valorDescontoNew == null)
            valorDescontoNew = BigDecimal.ZERO;

        return precoUnitarioNew.subtract(valorDescontoNew);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemVenda itemVenda = (ItemVenda) o;

        if (id != null ? !id.equals(itemVenda.id) : itemVenda.id != null) return false;
        if (venda != null ? !venda.equals(itemVenda.venda) : itemVenda.venda != null) return false;
        if (produto != null ? !produto.equals(itemVenda.produto) : itemVenda.produto != null) return false;
        if (precoUnitario != null ? !precoUnitario.equals(itemVenda.precoUnitario) : itemVenda.precoUnitario != null)
            return false;
        return valorDesconto != null ? valorDesconto.equals(itemVenda.valorDesconto) : itemVenda.valorDesconto == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (venda != null ? venda.hashCode() : 0);
        result = 31 * result + (produto != null ? produto.hashCode() : 0);
        result = 31 * result + (precoUnitario != null ? precoUnitario.hashCode() : 0);
        result = 31 * result + (valorDesconto != null ? valorDesconto.hashCode() : 0);
        return result;
    }
}
