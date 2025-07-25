package ifg.edu.br.model.entity;

public enum TipoConta {
    CONTA_CORRENTE("Conta Corrente"),
    POUPANCA("Poupança"),
    CARTEIRA("Carteira");

    private final String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}