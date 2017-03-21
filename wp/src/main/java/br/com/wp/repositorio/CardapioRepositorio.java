
package br.com.wp.repositorio;

import br.com.wp.modelo.Cardapio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */

public class CardapioRepositorio implements Serializable{
   
    @Inject
    private EntityManager em;

    public List<Cardapio> buscarCardapio()throws Exception {
       
        return em.createQuery("Select c From Cardapio c Order By c.nome").getResultList();
    }

    public void salvarAlterarItemCardapio(Cardapio itemCardapio) {
       
        if(itemCardapio.getId() == null){
            em.persist(itemCardapio);
        }else{
            em.merge(itemCardapio);
        }
        
    }

    public void excluirItemCardapio(Cardapio itemCardapio)throws Exception {
       
        Cardapio itemParaExcluir = em.find(Cardapio.class, itemCardapio.getId());
        em.remove(itemParaExcluir);
    }

    public Cardapio buscarItemCardapioPorId(long id)throws Exception {
      
        return em.find(Cardapio.class, id);
        
    }

    public List<Cardapio> buscarItemCardapioPorCategoria(Long id)throws Exception {
      
        System.out.println("categoria id "+id);
        return em.createQuery("Select c From Cardapio c where c.categoria.id= :id")
                .setParameter("id", id).getResultList();
        
    }

    public List<Cardapio> buscarCardapioParaAndroid() {
       
        boolean aux=true;
        return em.createQuery("Select c.id, c.nome, c.valor From Cardapio c where c.disponivel= :aux")
               .setParameter("aux", aux) .getResultList();
        
    }
}
