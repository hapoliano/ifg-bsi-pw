package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.Conta;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@Dependent
public class ContaDAO {

    @Inject
    EntityManager em;

    public void save(Conta conta) {
        em.persist(conta);
    }

    public List<Conta> findByUsuario(Usuario usuario) {
        return em.createQuery("FROM Conta c WHERE c.usuario = :usuario ORDER BY c.nome", Conta.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}