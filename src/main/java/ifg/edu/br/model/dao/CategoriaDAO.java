package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Categoria;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@Dependent
public class CategoriaDAO {

    @Inject
    EntityManager em;

    public void save(Categoria categoria) { // Método adicionado
        em.persist(categoria);
    }

    public Categoria find(Long id) {
        return em.find(Categoria.class, id);
    }

    public List<Categoria> listAll() {
        return em.createQuery("from Categoria order by nome", Categoria.class).getResultList();
    }
}