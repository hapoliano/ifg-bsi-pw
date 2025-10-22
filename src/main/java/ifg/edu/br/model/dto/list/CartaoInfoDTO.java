package ifg.edu.br.model.dto.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartaoInfoDTO {
    private String banco;
    private String dataValidade;
    private BigDecimal limiteCredito;
    private BigDecimal valorGasto;

    // Nova propriedade para a lista de faturas
    private List<FaturaInfoDTO> faturas = new ArrayList<>();

    // Getters e Setters
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }
    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
    public BigDecimal getValorGasto() { return valorGasto; }
    public void setValorGasto(BigDecimal valorGasto) { this.valorGasto = valorGasto; }

    // Getter e Setter para a nova lista
    public List<FaturaInfoDTO> getFaturas() { return faturas; }
    public void setFaturas(List<FaturaInfoDTO> faturas) { this.faturas = faturas; }
}