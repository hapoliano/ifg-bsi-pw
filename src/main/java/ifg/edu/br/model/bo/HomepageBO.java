package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.CartaoCreditoDAO;
import ifg.edu.br.model.dao.ContaDAO;
import ifg.edu.br.model.dao.DespesaDAO;
import ifg.edu.br.model.dto.HomepageDTO;
import ifg.edu.br.model.dto.list.DespesaListDTO;
import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class HomepageBO {

    @Inject
    CartaoCreditoDAO cartaoCreditoDAO;

    @Inject
    DespesaDAO despesaDAO;

    @Inject
    ContaDAO contaDAO;

    public HomepageDTO getHomepageData(Usuario usuario) {
        HomepageDTO dto = new HomepageDTO();
        List<CartaoCredito> cartoes = cartaoCreditoDAO.findByUsuario(usuario);

        dto.setLimiteTotalCartoes(cartoes.stream().map(CartaoCredito::getLimiteCredito).reduce(BigDecimal.ZERO, BigDecimal::add));
        dto.setGastoTotalCartoes(cartoes.stream().map(CartaoCredito::getValorGasto).reduce(BigDecimal.ZERO, BigDecimal::add));
        dto.setSaldoTotalContas(contaDAO.sumSaldoByUsuario(usuario));

        LocalDate hoje = LocalDate.now();
        dto.setTotalDespesasMesAtual(despesaDAO.sumDespesasByMonth(usuario, hoje.withDayOfMonth(1), hoje.withDayOfMonth(hoje.lengthOfMonth())));

        List<DespesaListDTO> ultimasDespesas = despesaDAO.findUltimasDespesas(usuario, 5)
                .stream()
                .map(DespesaListDTO::new)
                .collect(Collectors.toList());
        dto.setUltimasDespesas(ultimasDespesas);

        return dto;
    }
}