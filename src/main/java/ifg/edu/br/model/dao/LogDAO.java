package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Dependent
public class LogDAO {

    @Inject
    EntityManager em;

    public void save(Log log) {
        em.persist(log);
    }

    public List<Log> listAll() {
        return em.createQuery("FROM Log l ORDER BY l.dataHora DESC", Log.class)
                .getResultList();
    }
}
