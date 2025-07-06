package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class AuthBO {

    @Inject
    UsuarioDAO usuarioDAO;

    public boolean validarLogin(String email, String senha) {
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario == null) {
            return false;
        }
        return senha.equals(usuario.getSenha());
    }
}
