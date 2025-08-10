package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.ContaDAO;
import ifg.edu.br.model.dto.ContaDTO;
import ifg.edu.br.model.entity.Conta;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class ContaBO {

    @Inject
    ContaDAO contaDAO;

    @Inject
    LogBO logBO;

    @Transactional
    public void saveConta(ContaDTO dto, Usuario usuario) {
        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setNome(dto.getNome());
        conta.setTipo(dto.getTipo());
        conta.setSaldo(dto.getSaldo());

        contaDAO.save(conta);
        logBO.registrarAcao(usuario, "CADASTRO_CONTA - Nome: " + dto.getNome());
    }

    public List<Conta> findByUsuario(Usuario usuario) {
        return  contaDAO.findByUsuario(usuario);
    }
}