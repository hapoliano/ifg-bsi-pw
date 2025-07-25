package ifg.edu.br.model.dto.list;

import ifg.edu.br.model.entity.Despesa;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class DespesaListDTO {

    private String descricao;
    private BigDecimal valor;
    private String data;
    private String categoria;
    private String cartao;

    public DespesaListDTO(Despesa despesa) {
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.categoria = despesa.getCategoria() != null ? despesa.getCategoria().getNome() : "Sem Categoria";
        this.cartao = despesa.getCartaoCredito() != null ? despesa.getCartaoCredito().getBanco() : "Outros";
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getCartao() { return cartao; }
    public void setCartao(String cartao) { this.cartao = cartao; }
}