package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Despesa;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Dependent
public class DespesaDAO {

    @Inject
    EntityManager em;

    public void save(Despesa despesa) {
        em.persist(despesa);
    }
}