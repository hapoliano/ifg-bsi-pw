package ifg.edu.br.controller;

import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.list.LogListDTO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/logs")
public class LogController {

    @Inject
    LogBO logBO;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    Template logVisao;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLogPage(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER).location(URI.create("/auth/login")).build();
        }

        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
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

        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
        if (usuario == null || usuario.getTipo() != TipoUsuario.ADMIN) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<LogListDTO> logs = logBO.getLogs();
        return Response.ok(logs).build();
    }
}