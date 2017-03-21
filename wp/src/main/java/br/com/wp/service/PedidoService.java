package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Pedido;
import br.com.wp.enumeracao.StatusPedido;
import static br.com.wp.enumeracao.StatusPedido.PAGO;
import static br.com.wp.enumeracao.StatusPedido.PROCESSADO;
import br.com.wp.repositorio.PedidoRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class PedidoService implements Serializable {

    @Inject
    private PedidoRepositorio pedidoRepositorio;

    @Transactional
    public void salvarAlterarPedido(Pedido pedido) throws Exception {

        pedidoRepositorio.salvarAlterarPedido(pedido);
    }

  
    @Transactional
    public void alterarStatusPedido(List<Pedido> listaPedidosSelecionados) throws Exception {

        listaPedidosSelecionados.stream().forEach((pedido) -> {
            
            if (pedido.getStatusPedido().equals(PROCESSADO)) {
                
                pedido.setStatusPedido(StatusPedido.PROCESSANDO);
                
            } else {
                
                pedido.setStatusPedido(PROCESSADO);
            }
        });

        pedidoRepositorio.alterarStatusPedido(listaPedidosSelecionados);
    }

    @Transactional
    public void transferirPedidos(Cartao cartao, Cartao cartaoParaTranferir)throws Exception  {
       
        pedidoRepositorio.transferirPedidos(cartao,cartaoParaTranferir);
        
    }

    @Transactional
    public void alterarStatusParaPago(List<Pedido> listaPedidosPagar)throws Exception {
        
         listaPedidosPagar.stream().forEach((pedido) -> {
            
           pedido.setStatusPedido(StatusPedido.PAGO);
           
        });

        pedidoRepositorio.alterarStatusPedido(listaPedidosPagar);
              
    }

    public List<Pedido> buscarPedidosNaoPagos(StatusPedido statusPedido)throws Exception {
        
        return pedidoRepositorio.buscarPedidosNaoPagos(statusPedido);
    }
    
    public List<Pedido> buscarPedidosNaoPagosPorSecao(StatusPedido statusPedido,StatusPedido statusProcessando, String secao)throws Exception  {
     
        return pedidoRepositorio.buscarPedidosNaoPagosPorSecao(statusPedido,statusProcessando,secao);
        
    }

    /**
     *
     * @param listaPedidosSelecionados
     * @throws Exception
     */
    @Transactional
    public void excluirPedidos(List<Pedido> listaPedidosSelecionados)throws Exception {
        
        pedidoRepositorio.excluirPedidos(listaPedidosSelecionados);
        
    }
}
