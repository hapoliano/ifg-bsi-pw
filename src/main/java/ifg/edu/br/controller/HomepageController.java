package ifg.edu.br.controller;

import ifg.edu.br.model.bo.HomepageBO;
import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.HomepageDTO;
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

    @Inject
    HomepageBO homepageBO;

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
                return Response.status(Response.Status.SEE_OTHER)
                        .location(URI.create("/auth/login"))
                        .build();
            }

            HomepageDTO homepageData = homepageBO.getHomepageData(usuarioLogado);

            TemplateInstance templateInstance = homepage
                    .data("nomeDoUsuario", usuarioLogado.getNome())
                    .data("isAdmin", usuarioLogado.getTipo() == TipoUsuario.ADMIN)
                    .data("dashboard", homepageData);

            return Response.ok(templateInstance).build();

        } catch (NumberFormatException e) {
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .build();
        }
    }
}