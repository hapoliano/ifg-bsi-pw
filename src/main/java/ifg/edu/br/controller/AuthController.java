package ifg.edu.br.controller;

import ifg.edu.br.model.bo.AuthBO;
import ifg.edu.br.model.dto.LoginDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
        try {
            boolean loginValido = authBO.validarLogin(loginDTO.getEmail(), loginDTO.getSenha());

            if (loginValido) {
                return Response.ok().entity("{\"message\":\"Login bem-sucedido!\"}").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"E-mail ou senha inválidos\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Ocorreu um erro interno.\"}").build();
        }
    }
}