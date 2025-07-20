package ifg.edu.br.model.dto;

import java.math.BigDecimal;

public class CartaoCreditoDTO {
    private String banco;
    private String dataValidade; // Formato "MM/YY"
    private BigDecimal limiteCredito;
    private BigDecimal valorGasto; // Opcional, pode ser atualizado depois

    // Getters e Setters
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }
    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
    public BigDecimal getValorGasto() { return valorGasto; }
    public void setValorGasto(BigDecimal valorGasto) { this.valorGasto = valorGasto; }
}