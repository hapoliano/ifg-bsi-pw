package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Dependent
public class LogDAO {

    @Inject
    EntityManager em;

    public void save(Log log) {
        em.persist(log);
    }
}
