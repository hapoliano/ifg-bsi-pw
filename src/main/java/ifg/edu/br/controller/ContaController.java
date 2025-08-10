package ifg.edu.br.controller;

import ifg.edu.br.model.bo.ContaBO;
import ifg.edu.br.model.bo.UsuarioBO;
import ifg.edu.br.model.dto.ContaDTO;
import ifg.edu.br.model.entity.Conta;
import ifg.edu.br.model.entity.TipoUsuario;
import ifg.edu.br.model.entity.Usuario;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/contas")
public class ContaController {

    @Inject
    ContaBO contaBO;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    Template contaCadastro;

    @Inject
    Template contaVisaoGeral;

    @GET
    @Path("/cadastro")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaCadastro(@CookieParam("userId") String userId) {
        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
        return Response.ok(contaCadastro
                        .data("isAdmin", usuario.getTipo() == TipoUsuario.ADMIN)
                        .data("nomeDoUsuario", usuario.getNome()))
                .build();
    }

    @GET
    @Path("/visao-geral")
    @Produces(MediaType.TEXT_HTML)
    public Response getPaginaVisaoGeral(@CookieParam("userId") String userId) {
        Integer id = Integer.parseInt(userId);
        Usuario usuario = usuarioBO.getUsuarioEntity(id);
        List<Conta> contas = contaBO.findByUsuario(usuario);
        return Response.ok(contaVisaoGeral
                        .data("contas", contas)
                        .data("isAdmin", usuario.getTipo() == TipoUsuario.ADMIN)
                        .data("nomeDoUsuario", usuario.getNome()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarConta(ContaDTO dto, @CookieParam("userId") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            Integer id = Integer.parseInt(userId);
            Usuario usuario = usuarioBO.getUsuarioEntity(id);
            contaBO.saveConta(dto, usuario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}