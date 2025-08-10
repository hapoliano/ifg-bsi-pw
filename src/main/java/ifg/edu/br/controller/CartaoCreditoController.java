package ifg.edu.br.controller;

import ifg.edu.br.model.bo.CartaoCreditoBO;
import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.CartaoCreditoDTO;
import ifg.edu.br.model.dto.UsuarioDTO;
import ifg.edu.br.model.dto.list.VisaoGeralDTO;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/cartoes")
public class CartaoCreditoController {

    @Inject
    CartaoCreditoBO bo;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    LogBO logBO;

    @Inject
    Template cartaoCadastro;

    @Inject
    Template cartaoVisaoGeral;

    @GET
    @Path("/cadastro")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaCadastro(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return redirectToLoginWithClearCookie();
        }

        try {
            Integer id = Integer.parseInt(userId);
            UsuarioDTO usuarioLogado = usuarioBO.getUsuarioDTO(id);

            if (usuarioLogado == null) {
                return redirectToLoginWithClearCookie();
            }

            boolean isAdmin = usuarioLogado.getTipo() == TipoUsuario.ADMIN;

            TemplateInstance templateInstance = cartaoCadastro
                    .data("isAdmin", isAdmin)
                    .data("nomeDoUsuario", usuarioLogado.getNome());

            return Response.ok(templateInstance).build();
        } catch (NumberFormatException e) {
            return redirectToLoginWithClearCookie();
        }
    }

    @GET
    @Path("/visao-geral")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaVisaoGeral(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return redirectToLoginWithClearCookie();
        }

        try {
            Integer id = Integer.parseInt(userId);
            Usuario usuario = usuarioBO.getUsuarioEntity(id);

            if (usuario == null) {
                logBO.registrarAcao(null, "TENTATIVA_ACESSO_INVALIDA - Usuário não encontrado com ID do cookie: " + userId);
                return redirectToLoginWithClearCookie();
            }

            VisaoGeralDTO visaoGeral = bo.getVisaoGeral(usuario);

            return Response.ok(cartaoVisaoGeral
                            .data("visao", visaoGeral)
                            .data("isAdmin", usuario.getTipo() == TipoUsuario.ADMIN)
                            .data("nomeDoUsuario", usuario.getNome()))
                    .build();

        } catch (NumberFormatException e) {
            return redirectToLoginWithClearCookie();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvarCartao(CartaoCreditoDTO dto, @CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Integer id = Integer.parseInt(userId);
            Usuario usuario = usuarioBO.getUsuarioEntity(id);
            if (usuario == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não encontrado.").build();
            }
            bo.saveCartao(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    private Response redirectToLoginWithClearCookie() {
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