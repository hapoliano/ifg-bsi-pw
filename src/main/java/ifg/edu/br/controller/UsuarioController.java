package ifg.edu.br.controller;

import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.UsuarioDTO;
import ifg.edu.br.model.dto.list.UsuarioListDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuario")
public class UsuarioController {

    @Inject
    Template usuarioCadastro;

    @Inject
    UsuarioBO usuarioBO;

    @GET
    @Path("cadastro")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getView() {
        return usuarioCadastro.instance();
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
