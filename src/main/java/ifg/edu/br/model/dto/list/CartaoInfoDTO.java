package ifg.edu.br.model.dto.list;

import java.math.BigDecimal;

public class CartaoInfoDTO {
    private Long id;
    private String banco;
    private String dataValidade;
    private BigDecimal limiteCredito;
    private BigDecimal valorGasto;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }
    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
    public BigDecimal getValorGasto() { return valorGasto; }
    public void setValorGasto(BigDecimal valorGasto) { this.valorGasto = valorGasto; }
}