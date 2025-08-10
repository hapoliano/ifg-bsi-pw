package ifg.edu.br.controller;

import ifg.edu.br.model.bo.CartaoCreditoBO;
import ifg.edu.br.model.bo.CategoriaBO;
import ifg.edu.br.model.bo.DespesaBO;
import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.DespesaDTO;
import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Categoria;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/despesas")
public class DespesaController {

    @Inject
    DespesaBO bo;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    CategoriaBO categoriaBO;

    @Inject
    CartaoCreditoBO cartaoCreditoBO;

    @Inject
    Template despesaCadastro;

    @GET
    @Path("/cadastro")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaCadastro(@CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.SEE_OTHER).location(URI.create("/auth/login")).build();
        }

        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
        List<CartaoCredito> cartoes = cartaoCreditoBO.findByUsuario(usuario);
        List<Categoria> categorias = categoriaBO.listAll();

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
            Integer id = Integer.parseInt(userId);
            Usuario usuario = usuarioBO.getUsuarioEntity(id);
            bo.saveDespesa(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}