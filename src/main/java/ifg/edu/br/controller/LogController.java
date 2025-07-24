package ifg.edu.br.controller;

import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.list.LogListDTO;
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

@Path("/logs")
public class LogController {

    @Inject
    LogBO logBO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    Template logVisao;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLogPage(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER).location(URI.create("/auth/login")).build();
        }

        Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
        if (usuario == null || usuario.getTipo() != TipoUsuario.ADMIN) {
            return Response.status(Response.Status.FORBIDDEN).location(URI.create("/homepage")).build();
        }

        return Response.ok(logVisao
                        .data("isAdmin", true)
                        .data("nomeDoUsuario", usuario.getNome()))
                .build();
    }

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogData(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
        if (usuario == null || usuario.getTipo() != TipoUsuario.ADMIN) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<LogListDTO> logs = logBO.getLogs();
        return Response.ok(logs).build();
    }
}