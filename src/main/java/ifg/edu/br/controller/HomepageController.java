package ifg.edu.br.controller;

import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/")
public class HomepageController {

    @Inject
    Template homepage;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    LogBO logBO;

    @GET
    @Path("/homepage")
    @Produces(MediaType.TEXT_HTML)
    public Response getHomepage(@CookieParam("userId") String userId) {

        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .build();
        }

        try {
            Integer id = Integer.parseInt(userId);
            Usuario usuarioLogado = usuarioDAO.find(id);

            if (usuarioLogado == null) {
                logBO.registrarAcao(null, "TENTATIVA_ACESSO_INVALIDA - Usuário não encontrado: " + id);
                return Response.status(Response.Status.SEE_OTHER)
                        .location(URI.create("/auth/login"))
                        .build();
            }

            boolean isAdmin = usuarioLogado.getTipo() == TipoUsuario.ADMIN;

            TemplateInstance templateInstance = homepage
                    .data("nomeDoUsuario", usuarioLogado.getNome())
                    .data("isAdmin", isAdmin);

            return Response.ok(templateInstance).build();

        } catch (NumberFormatException e) {
            logBO.registrarAcao(null, "ACESSO_NAO_AUTORIZADO - Cookie de usuário inválido");
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .build();
        }
    }
}