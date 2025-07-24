package ifg.edu.br.filter;

import ifg.edu.br.model.bo.LogBO;
import ifg.edu.br.model.dao.UsuarioDAO;
import ifg.edu.br.model.entity.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {

    @Inject
    LogBO logBO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // Evita o log de recursos estáticos para não poluir o log
        if (path.startsWith("css/") || path.startsWith("js/") || path.startsWith("images/")) {
            return;
        }

        Cookie userIdCookie = requestContext.getCookies().get("userId");

        Usuario usuario = null;
        if (userIdCookie != null && !userIdCookie.getValue().isEmpty()) {
            try {
                Integer userId = Integer.parseInt(userIdCookie.getValue());
                usuario = usuarioDAO.find(userId);
            } catch (NumberFormatException e) {
                // Ignora o cookie se ele for inválido
            }
        }

        String action = "ACESSO_PAGINA: " + requestContext.getMethod() + " " + path;
        logBO.registrarAcao(usuario, action);
    }
}