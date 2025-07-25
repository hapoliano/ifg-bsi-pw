package ifg.edu.br.model.dto;

import ifg.edu.br.model.dto.list.DespesaListDTO;
import java.math.BigDecimal;
import java.util.List;

public class HomepageDTO {

    private BigDecimal limiteTotalCartoes;
    private BigDecimal gastoTotalCartoes;
    private BigDecimal totalDespesasMesAtual;
    private BigDecimal saldoTotalContas;
    private List<DespesaListDTO> ultimasDespesas;

    public BigDecimal getSaldoTotalContas() { return saldoTotalContas; }
    public void setSaldoTotalContas(BigDecimal saldoTotalContas) { this.saldoTotalContas = saldoTotalContas; }

    public BigDecimal getLimiteTotalCartoes() { return limiteTotalCartoes; }
    public void setLimiteTotalCartoes(BigDecimal limiteTotalCartoes) { this.limiteTotalCartoes = limiteTotalCartoes; }
    public BigDecimal getGastoTotalCartoes() { return gastoTotalCartoes; }
    public void setGastoTotalCartoes(BigDecimal gastoTotalCartoes) { this.gastoTotalCartoes = gastoTotalCartoes; }
    public BigDecimal getTotalDespesasMesAtual() { return totalDespesasMesAtual; }
    public void setTotalDespesasMesAtual(BigDecimal totalDespesasMesAtual) { this.totalDespesasMesAtual = totalDespesasMesAtual; }
    public List<DespesaListDTO> getUltimasDespesas() { return ultimasDespesas; }
    public void setUltimasDespesas(List<DespesaListDTO> ultimasDespesas) { this.ultimasDespesas = ultimasDespesas; }
}