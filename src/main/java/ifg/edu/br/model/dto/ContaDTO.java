package ifg.edu.br.model.dto;

import ifg.edu.br.model.entity.TipoConta;
import java.math.BigDecimal;

public class ContaDTO {

    private String nome;
    private TipoConta tipo;
    private BigDecimal saldo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoConta getTipo() { return tipo; }
    public void setTipo(TipoConta tipo) { this.tipo = tipo; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}