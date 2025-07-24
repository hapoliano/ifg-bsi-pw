package ifg.edu.br.bootstrap;

import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ApplicationScoped
public class Bootstrap {

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    LogBO logBO;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        if (usuarioDAO.findByEmail("admin@bytebank.com") != null) {
            return;
        }

        System.out.println("### EXECUTANDO BOOTSTRAP ###");

        Usuario admin1 = new Usuario();
        admin1.setNome("Daniel Apoliano");
        admin1.setEmail("apoliano@bytebank.com");
        admin1.setSenha(passwordEncoder.encode("1234"));
        admin1.setTipo(TipoUsuario.ADMIN);
        usuarioDAO.save(admin1);

        Usuario user1 = new Usuario();
        user1.setNome("Eduarda Carvalho");
        user1.setEmail("duda@bytebank.com");
        user1.setSenha(passwordEncoder.encode("1234"));
        user1.setTipo(TipoUsuario.USUARIO);
        usuarioDAO.save(user1);

        logBO.registrarAcao(null, "BOOTSTRAP - Usuários iniciais (1 Admin, 1 Usuário) criados com sucesso.");
        System.out.println("### BOOTSTRAP FINALIZADO ###");
    }
}