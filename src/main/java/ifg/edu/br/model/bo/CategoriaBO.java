package ifg.edu.br.model.bo;

import ifg.edu.br.model.dao.CategoriaDAO;
import ifg.edu.br.model.entity.Categoria;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class CategoriaBO {

    @Inject
    CategoriaDAO categoriaDAO;

    public List<Categoria> listAll() {
        return categoriaDAO.listAll();
    }
}
