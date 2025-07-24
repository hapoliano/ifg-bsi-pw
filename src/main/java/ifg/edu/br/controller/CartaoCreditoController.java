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

        if (userId == null || userId.isEmpty()) {
            logBO.registrarAcao(null, "ACESSO_NAO_AUTORIZADO - /cartoes/cadastro");
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

            TemplateInstance templateInstance = cartaoCadastro
                    .data("isAdmin", isAdmin)
                    .data("nomeDoUsuario", usuarioLogado.getNome());

            return Response.ok(templateInstance).build();

        } catch (NumberFormatException e) {
            logBO.registrarAcao(null, "ACESSO_NAO_AUTORIZADO - Cookie de usuário inválido");
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
            logBO.registrarAcao(null, "ACESSO_NAO_AUTORIZADO - /cartoes/visao-geral");
            return Response.seeOther(URI.create("/auth/login")).build();
        }
        Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
        VisaoGeralDTO visaoGeral = bo.getVisaoGeral(usuario);

        return Response.ok(cartaoVisaoGeral
                        .data("visao", visaoGeral)
                        .data("isAdmin", usuario.getTipo() == TipoUsuario.ADMIN)
                        .data("nomeDoUsuario", usuario.getNome()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvarCartao(CartaoCreditoDTO dto, @CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            logBO.registrarAcao(null, "TENTATIVA_SALVAR_CARTAO_NAO_AUTORIZADA");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
            bo.saveCartao(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}