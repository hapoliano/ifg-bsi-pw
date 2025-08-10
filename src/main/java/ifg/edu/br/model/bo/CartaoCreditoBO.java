package ifg.edu.br.model.bo;

// ... outros imports
import ifg.edu.br.model.dao.CartaoCreditoDAO;
import ifg.edu.br.model.dto.CartaoCreditoDTO;
import ifg.edu.br.model.dto.list.CartaoInfoDTO;
import ifg.edu.br.model.dto.list.VisaoGeralDTO;
import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class CartaoCreditoBO {

    @Inject
    CartaoCreditoDAO dao;

    @Inject
    LogBO logBO;

    @Transactional
    public void saveCartao(CartaoCreditoDTO dto, Usuario usuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth ym = YearMonth.parse(dto.getDataValidade(), formatter);
        LocalDate dataValidade = ym.atEndOfMonth();

        if (dataValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de validade do cartão já expirou.");
        }

        CartaoCredito cartao = new CartaoCredito();
        cartao.setUsuario(usuario);
        cartao.setBanco(dto.getBanco());
        cartao.setDataValidade(dataValidade);
        cartao.setLimiteCredito(dto.getLimiteCredito());

        if (dto.getValorGasto() != null) {
            cartao.setValorGasto(dto.getValorGasto());
        }

        dao.save(cartao);

        logBO.registrarAcao(usuario, "CADASTRO_CARTAO - Banco: " + dto.getBanco());
    }

    public VisaoGeralDTO getVisaoGeral(Usuario usuario) {
        List<CartaoCredito> cartoesDoUsuario = dao.findByUsuario(usuario);

        BigDecimal limiteTotal = cartoesDoUsuario.stream()
                .map(CartaoCredito::getLimiteCredito)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gastoTotal = cartoesDoUsuario.stream()
                .map(CartaoCredito::getValorGasto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartaoInfoDTO> cartoesInfo = cartoesDoUsuario.stream()
                .map(this::toCartaoInfoDTO)
                .collect(Collectors.toList());

        VisaoGeralDTO visaoGeral = new VisaoGeralDTO();
        visaoGeral.setLimiteTotal(limiteTotal);
        visaoGeral.setGastoTotal(gastoTotal);
        visaoGeral.setCartoes(cartoesInfo);

        return visaoGeral;
    }

    private CartaoInfoDTO toCartaoInfoDTO(CartaoCredito cartao) {
        CartaoInfoDTO dto = new CartaoInfoDTO();
        dto.setBanco(cartao.getBanco());
        dto.setDataValidade(cartao.getDataValidade().format(DateTimeFormatter.ofPattern("MM/yyyy")));
        dto.setLimiteCredito(cartao.getLimiteCredito());
        dto.setValorGasto(cartao.getValorGasto());
        return dto;
    }

    public List<CartaoCredito> findByUsuario(Usuario usuario) {
        return dao.findByUsuario(usuario);
    }
}