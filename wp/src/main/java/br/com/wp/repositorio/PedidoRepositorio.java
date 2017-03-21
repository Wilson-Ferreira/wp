package br.com.wp.repositorio;

import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Pedido;
import br.com.wp.enumeracao.StatusPedido;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wilson F Florindo
 */

public class PedidoRepositorio implements Serializable {

    @Inject
    private EntityManager em;
 
    public void salvarAlterarPedido(Pedido pedido) throws Exception {

        if (pedido.getId() == null) {
            
            em.persist(pedido);
            
        } else {
            
            em.merge(pedido);
        }
    }

    public void alterarStatusPedido(List<Pedido> listaPedidosSelecionados) throws Exception {

        listaPedidosSelecionados.stream().forEach((pedido) -> {
            em.merge(pedido);
        });
    }

    public void transferirPedidos(Cartao cartao, Cartao cartaoParaTranferir)throws Exception  {
        
        String query = "Update Pedido p set p.cartao= :cartaoParaTranferir where p.cartao= :cartao";
        Query sQuery = em.createQuery(query);
        
        sQuery.setParameter("cartao", cartao);
        sQuery.setParameter("cartaoParaTranferir", cartaoParaTranferir);        
        
        sQuery.executeUpdate();
    }

    public List<Pedido> buscarPedidosNaoPagosPorSecao(StatusPedido statusPedido,StatusPedido statusProcessando, String secao)throws Exception  {
       
        return em.createQuery("Select p from Pedido p where p.statusPedido<> :statusPedido and p.statusPedido= :statusProcessando and p.itemCardapio.categoria.secao= :secao")
                .setParameter("statusPedido", statusPedido).setParameter("secao", secao)
                .setParameter("statusProcessando", statusProcessando).getResultList();
        
    }

    public List<Pedido> buscarPedidosNaoPagos(StatusPedido statusPedido)throws Exception  {
       
         return em.createQuery("Select p from Pedido p where p.statusPedido<> :statusPedido")
                .setParameter("statusPedido", statusPedido).getResultList();
        
    }

    public void excluirPedidos(List<Pedido> listaPedidosSelecionados) throws Exception {
       
        listaPedidosSelecionados.stream().map((ped) -> (Pedido) em.createQuery("Select p from Pedido p where p.id= :id")
                .setParameter("id", ped.getId()).getSingleResult()).forEach((pedidoRemover) -> {
                    em.remove(pedidoRemover);
        });
    }
}
