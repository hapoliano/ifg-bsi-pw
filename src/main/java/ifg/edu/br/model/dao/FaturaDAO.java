package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Fatura;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Dependent
public class FaturaDAO {

    @Inject
    EntityManager em;

    public List<Fatura> findByCartao(CartaoCredito cartao) {
        String hql = "FROM Fatura f WHERE f.cartaoCredito = :cartao ORDER BY f.mesReferencia DESC";
        return em.createQuery(hql, Fatura.class)
                .setParameter("cartao", cartao)
                .getResultList();
    }
}