package ifg.edu.br.controller;

import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.UsuarioDTO;
import ifg.edu.br.model.dto.list.UsuarioListDTO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
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

    @GET
    @Path("cadastro")
    @Produces(MediaType.TEXT_HTML)
    public Response getView(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER).location(URI.create("/auth/login")).build();
        }

        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
        if (usuario == null || usuario.getTipo() != TipoUsuario.ADMIN) {
            return Response.status(Response.Status.FORBIDDEN).location(URI.create("/homepage")).build();
        }

        return Response.ok(usuarioCadastro
                        .data("isAdmin", true)
                        .data("nomeDoUsuario", usuario.getNome()))
                .build();
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