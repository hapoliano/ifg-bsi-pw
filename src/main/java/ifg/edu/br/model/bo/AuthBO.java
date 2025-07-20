package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Dependent
public class AuthBO {

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    LogBO logBO;

    public Usuario validarLogin(String email, String senha) {
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                logBO.registrarAcao(usuario, "LOGIN_SUCESSO");
                return usuario;
            }
        }
        return null;
    }
}
