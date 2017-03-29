/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.enumeracao.NumeroCartao;
import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Configuracao;
import br.com.wp.modelo.Pedido;
import br.com.wp.service.CartaoService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.PedidoService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */

@Path("/pedido")
public class PedidoREST {

    @Inject
    private Configuracao configuracao;
    @Inject
    private Cartao cartao;
    @Inject
    private PedidoService pedidoService;
    @Inject
    private CartaoService cartaoService;
    @Inject
    private ConfiguracaoService configuracaoService;
    List<Pedido> listaPedidos = new ArrayList<>();

    @POST
    @Path("/salvarPedidos")
    @Consumes("application/json")
    @Produces("application/json")
    public String salvarPedidos(String jsonPedido) {

    
        try {
            
            Gson gson = new Gson();

            Type type = new TypeToken<List<Pedido>>() {
            }.getType();
            
            listaPedidos = gson.fromJson(jsonPedido, type);

            configuracao = configuracaoService.buscarConfiguracoes();

            if (configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.MESA.toString())) {

                cartao = cartaoService.buscarCartaoNumeroZero(NumeroCartao.ZERO.numero());
            }

            for (Pedido p : listaPedidos) {

                if (configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.MESA.toString())) {

                    p.setCartao(cartao);
                }

                pedidoService.salvarAlterarPedido(p);
            }

        } catch (Exception ex) {

           return "Erro ao salvar";
          
        }
        return "Pedidos salvos";
    }

}
