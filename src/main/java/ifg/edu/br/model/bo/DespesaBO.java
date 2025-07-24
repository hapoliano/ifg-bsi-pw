package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.CartaoCreditoDAO;
import ifg.edu.br.model.dao.CategoriaDAO;
import ifg.edu.br.model.dao.DespesaDAO;
import ifg.edu.br.model.dto.DespesaDTO;
import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Categoria;
import ifg.edu.br.model.entity.Despesa;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Dependent
public class DespesaBO {

    @Inject
    DespesaDAO despesaDAO;

    @Inject
    CartaoCreditoDAO cartaoCreditoDAO;

    @Inject
    CategoriaDAO categoriaDAO;

    @Inject
    LogBO logBO;

    @Transactional
    public void saveDespesa(DespesaDTO dto, Usuario usuario) {
        Despesa despesa = new Despesa();
        despesa.setUsuario(usuario);
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(LocalDate.parse(dto.getData(), DateTimeFormatter.ISO_LOCAL_DATE));

        if (dto.getCartaoId() != null) {
            CartaoCredito cartao = cartaoCreditoDAO.find(dto.getCartaoId());
            despesa.setCartaoCredito(cartao);
            cartao.setValorGasto(cartao.getValorGasto().add(despesa.getValor()));
        }

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaDAO.find(dto.getCategoriaId());
            despesa.setCategoria(categoria);
        }

        despesaDAO.save(despesa);
        logBO.registrarAcao(usuario, "CADASTRO_DESPESA - Valor: R$" + dto.getValor());
    }
}