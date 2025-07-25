package ifg.edu.br.controller;

import ifg.edu.br.model.bo.CartaoCreditoBO;
import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.CartaoCreditoDTO;
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
    UsuarioDAO usuarioDAO;

    @Inject
    CartaoCreditoBO bo;

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
        // 1. Verifica se o usuário está logado
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .build();
        }

        try {
            // 2. Busca o usuário no banco de dados
            Integer id = Integer.parseInt(userId);
            Usuario usuarioLogado = usuarioDAO.find(id);

            if (usuarioLogado == null) {
                // Se não encontrar o usuário, redireciona para o login
                return Response.status(Response.Status.SEE_OTHER)
                        .location(URI.create("/auth/login"))
                        .build();
            }

            // 3. Verifica se o usuário é um administrador
            boolean isAdmin = usuarioLogado.getTipo() == TipoUsuario.ADMIN;

            // 4. Prepara a instância do template com os dados corretos
            TemplateInstance templateInstance = cartaoCadastro
                    .data("isAdmin", isAdmin)
                    .data("nomeDoUsuario", usuarioLogado.getNome());

            return Response.ok(templateInstance).build();

        } catch (NumberFormatException e) {
            // Se o cookie for inválido, redireciona para o login
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .build();
        }
    }

    @GET
    @Path("/visao-geral")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaVisaoGeral(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.seeOther(URI.create("/auth/login")).build();
        }

        try {
            Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));

            if (usuario == null) {
                logBO.registrarAcao(null, "TENTATIVA_ACESSO_INVALIDA - Usuário não encontrado com ID do cookie: " + userId);
                NewCookie cookie = new NewCookie.Builder("userId").value("").path("/").maxAge(0).build();
                return Response.status(Response.Status.SEE_OTHER)
                        .location(URI.create("/auth/login"))
                        .cookie(cookie)
                        .build();
            }

            VisaoGeralDTO visaoGeral = bo.getVisaoGeral(usuario);

            return Response.ok(cartaoVisaoGeral
                            .data("visao", visaoGeral)
                            .data("isAdmin", usuario.getTipo() == TipoUsuario.ADMIN)
                            .data("nomeDoUsuario", usuario.getNome()))
                    .build();

        } catch (NumberFormatException e) {
            NewCookie cookie = new NewCookie.Builder("userId").value("").path("/").maxAge(0).build();
            return Response.status(Response.Status.SEE_OTHER)
                    .location(URI.create("/auth/login"))
                    .cookie(cookie)
                    .build();
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
            Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
            if (usuario == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não encontrado.").build();
            }
            bo.saveCartao(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarCartao(@PathParam("id") Long id, @CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
            if (usuario == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não encontrado.").build();
            }
            bo.deleteCartao(id, usuario);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}