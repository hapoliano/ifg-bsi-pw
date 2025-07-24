package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.LogDAO;
import ifg.edu.br.model.dto.list.LogListDTO;
import ifg.edu.br.model.entity.Log;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LogBO {
    @Inject
    LogDAO dao;

    @Transactional
    public void registrarAcao(Usuario usuario, String acao) {
        Log novoLog = new Log();
        if (usuario != null) {
            novoLog.setUsuario(usuario);
        }
        novoLog.setAcao(acao);
        dao.save(novoLog);
    }

    public List<LogListDTO> getLogs() {
        return dao.listAll().stream()
                .map(LogListDTO::new)
                .collect(Collectors.toList());
    }
}
