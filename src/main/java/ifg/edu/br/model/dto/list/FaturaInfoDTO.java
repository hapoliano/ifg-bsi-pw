package ifg.edu.br.model.dto.list;

import ifg.edu.br.model.entity.Fatura;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FaturaInfoDTO {

    private String mesReferencia;
    private BigDecimal valorTotal;
    private String dataVencimento;

    // Construtor que converte a Entidade para DTO
    public FaturaInfoDTO(Fatura fatura) {
        // Formata o mês de referência (Ex: "Outubro/2025")
        DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR"));
        this.mesReferencia = fatura.getMesReferencia().format(mesFormatter);

        this.valorTotal = fatura.getValorTotalFatura();

        // Formata a data de vencimento (Ex: "10/11/2025")
        DateTimeFormatter diaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataVencimento = fatura.getDataVencimento().format(diaFormatter);
    }

    // Getters e Setters
    public String getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(String mesReferencia) { this.mesReferencia = mesReferencia; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public String getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(String dataVencimento) { this.dataVencimento = dataVencimento; }
}