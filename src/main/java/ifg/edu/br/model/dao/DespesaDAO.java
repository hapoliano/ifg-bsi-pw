package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Despesa;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Dependent
public class DespesaDAO {

    @Inject
    EntityManager em;

    public void save(Despesa despesa) {
        em.persist(despesa);
    }

    public List<Despesa> findUltimasDespesas(Usuario usuario, int limit) {
        return em.createQuery("FROM Despesa d WHERE d.usuario = :usuario ORDER BY d.data DESC", Despesa.class)
                .setParameter("usuario", usuario)
                .setMaxResults(limit)
                .getResultList();
    }

    public BigDecimal sumDespesasByMonth(Usuario usuario, LocalDate startOfMonth, LocalDate endOfMonth) {
        BigDecimal result = em.createQuery(
                        "SELECT SUM(d.valor) FROM Despesa d WHERE d.usuario = :usuario AND d.data BETWEEN :start AND :end", BigDecimal.class)
                .setParameter("usuario", usuario)
                .setParameter("start", startOfMonth)
                .setParameter("end", endOfMonth)
                .getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }
}