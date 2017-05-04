package br.com.wp.controle.pedido;

import br.com.wp.enumeracao.NomeSecao;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.service.PedidoService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@RequestScoped
public class AlterarStatusPedidoBean implements Serializable {

    @Inject
    private ListarPedidoBean listarPedidoBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private PedidoService pedidoService;

    public void alterarStatusPedido(){

        try {

            pedidoService.alterarStatusPedido(listarPedidoBean.getListaPedidosSelecionados());

            jsfUtil.addMensagemInfo("Status alterado com sucesso");

            if (!listarPedidoBean.getSecao().equalsIgnoreCase(NomeSecao.caixa.toString())) {

                listarPedidoBean.getListaPedidos().removeAll(listarPedidoBean.getListaPedidosSelecionados());
            }

            listarPedidoBean.instanciarPedidos();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }
}
