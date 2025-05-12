package ifg.edu.br;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/teste2")
public class Teste2 {

    @Inject
    Template index2;

    @GET
    @Produces
    public TemplateInstance getHtml() {
        return index2.instance();
    }
}
