package rio.brunorodrigues.batchprogram.dto;

public class ReportDTO {

    private String dataProcessamento;
    private String valorDesconto;
    private String valorUnitario;
    private String valorTotal;
    private String produto;
    private String loja;
    private String pdv;

    public String getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(String dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getPdv() {
        return pdv;
    }

    public void setPdv(String pdv) {
        this.pdv = pdv;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "dataProcessamento='" + dataProcessamento + '\'' +
                ", valorDesconto='" + valorDesconto + '\'' +
                ", valorUnitario='" + valorUnitario + '\'' +
                ", valorTotal='" + valorTotal + '\'' +
                ", produto='" + produto + '\'' +
                '}';
    }
}
