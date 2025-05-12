package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.UsuarioDTO;
import ifg.edu.br.model.dto.list.UsuarioListDTO;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Dependent
public class UsuarioBO {

    @Inject
    UsuarioDAO dao;

    public List<UsuarioListDTO> getUsuarios() {
        return dao.getAllUsuario();
    }

    @Transactional
    public Response saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        dao.save(usuario);
        return Response.status(Response.Status.CREATED).entity(usuario.getId()).build();
    }
}
