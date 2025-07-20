package ifg.edu.br.model.dto.list;

import java.math.BigDecimal;
import java.util.List;

public class VisaoGeralDTO {
    private BigDecimal limiteTotal;
    private BigDecimal gastoTotal;
    private List<CartaoInfoDTO> cartoes;

    public BigDecimal getLimiteTotal() {
        return limiteTotal;
    }

    public void setLimiteTotal(BigDecimal limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public BigDecimal getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(BigDecimal gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public List<CartaoInfoDTO> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoInfoDTO> cartoes) {
        this.cartoes = cartoes;
    }
}