package ifg.edu.br.controller;

import ifg.edu.br.model.bo.AuthBO;
import ifg.edu.br.model.dto.LoginDTO;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    Template login;

    @Inject
    AuthBO authBO;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLoginPage() {
        return login.instance();
    }

    @POST
    @Path("/login")
    public Response doLogin(LoginDTO loginDTO) {
        Usuario usuarioLogado = authBO.validarLogin(loginDTO.getEmail(), loginDTO.getSenha());

        if (usuarioLogado != null) {
            NewCookie cookie = new NewCookie.Builder("userId")
                    .value(usuarioLogado.getId().toString())
                    .path("/")
                    .maxAge(3600)
                    .build();

            return Response.ok("{\"message\":\"Login bem-sucedido!\"}")
                    .cookie(cookie)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"E-mail ou senha inválidos\"}")
                    .build();
        }
    }

    @GET
    @Path("/logout")
    public Response doLogout() {
        NewCookie cookie = new NewCookie.Builder("userId")
                .value("")
                .path("/")
                .maxAge(0)
                .build();

        return Response.status(Response.Status.SEE_OTHER)
                .location(URI.create("/auth/login"))
                .cookie(cookie)
                .build();
    }
}