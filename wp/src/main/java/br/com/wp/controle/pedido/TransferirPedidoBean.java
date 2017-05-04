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
public class TransferirPedidoBean implements Serializable {

    @Inject
    private ListarPedidoBean listarPedidoBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private PedidoService pedidoService;

    public void preparaTransferirPedidos() {

        if (!listarPedidoBean.verificaSeHaMaisDeUmPedidoSelecionadoNaLista()) {

            try {

                listarPedidoBean.buscarMesasFuncionariosQuantidadesCategoriasCartoes();

                listarPedidoBean.buscarItemCardapioPorCategoria(null);

            } catch (Exception ex) {

                UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {

                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                } else {

                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
                }
            }

            jsfUtil.preparaTranferirPedidos();
        }
    }

    public void tranferirPedidos() throws Exception {

        try {

            pedidoService.transferirPedidos(listarPedidoBean.getPedido().getCartao(), listarPedidoBean.getCartao());

            listarPedidoBean.buscarPedidosPorSecao();

            jsfUtil.addMensagemInfo("Pedidos tranferidos com sucesso");

            listarPedidoBean.setListaPedidosSelecionados(null);

            listarPedidoBean.instanciarPedidos();

            jsfUtil.listar();

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
