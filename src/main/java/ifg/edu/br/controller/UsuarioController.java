package ifg.edu.br.controller;

import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.UsuarioDTO;
import ifg.edu.br.model.dto.list.UsuarioListDTO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/usuario")
public class UsuarioController {

    @Inject
    Template usuarioCadastro;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    UsuarioDAO usuarioDAO;

    @GET
    @Path("cadastro")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getView() {
        return usuarioCadastro
                .data("isAdmin", true)
                .data("nomeDoUsuario", "Admin Teste");
    }

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioListDTO> listarUsuarios() {
        return usuarioBO.getUsuarios();
    }

    @POST
    @Path("salvar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarUsuario(UsuarioDTO usuarioDTO) {
        return usuarioBO.saveUsuario(usuarioDTO);
    }
}