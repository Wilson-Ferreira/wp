package br.com.wp.controle.pedido;

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
public class ExcluirPedidoBean implements Serializable {

    @Inject
    private ListarPedidoBean listarPedidoBean;
    @Inject
    private PedidoService pedidoService;
    @Inject
    private JsfUtil jsfUtil;

    public void preparaExcluirPedido() {

        jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirPedido() {

        try {

            pedidoService.excluirPedidos(listarPedidoBean.getListaPedidosSelecionados());

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex.getMessage());
            }
        }

        listarPedidoBean.getListaPedidos().removeAll(listarPedidoBean.getListaPedidosSelecionados());

        listarPedidoBean.instanciarPedidos();

        jsfUtil.addMensagemInfo("Pedidos excluidos com sucesso");
    }
}
