package ifg.edu.br.model.dao;

import ifg.edu.br.model.entity.CartaoCredito;
import ifg.edu.br.model.entity.Usuario;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@Dependent
public class CartaoCreditoDAO {

    @Inject
    EntityManager em;

    public void save(CartaoCredito cartao) {
        em.persist(cartao);
    }

    public List<CartaoCredito> findByUsuario(Usuario usuario) {
        String hql = "from CartaoCredito c where c.usuario = :usuario";
        return em.createQuery(hql, CartaoCredito.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}
