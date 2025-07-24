package ifg.edu.br.controller;

import ifg.edu.br.model.bo.DespesaBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.dto.DespesaDTO;
import ifg.edu.br.model.entity.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import ifg.edu.br.model.dao.CartaoCreditoDAO;
import ifg.edu.br.model.dao.CategoriaDAO;
import java.util.List;
import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Categoria;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/despesas")
public class DespesaController {

    @Inject
    DespesaBO bo;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    CartaoCreditoDAO cartaoCreditoDAO;

    @Inject
    CategoriaDAO categoriaDAO;

    @Inject
    Template despesaCadastro;

    @GET
    @Path("/cadastro")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaCadastro(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER).location(URI.create("/auth/login")).build();
        }

        Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
        List<CartaoCredito> cartoes = cartaoCreditoDAO.findByUsuario(usuario);
        List<Categoria> categorias = categoriaDAO.listAll();

        TemplateInstance template = despesaCadastro
                .data("cartoes", cartoes)
                .data("categorias", categorias)
                .data("isAdmin", usuario.getTipo() == ifg.edu.br.model.entity.TipoUsuario.ADMIN)
                .data("nomeDoUsuario", usuario.getNome());

        return Response.ok(template).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarDespesa(DespesaDTO dto, @CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Usuario usuario = usuarioDAO.find(Integer.parseInt(userId));
            bo.saveDespesa(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}