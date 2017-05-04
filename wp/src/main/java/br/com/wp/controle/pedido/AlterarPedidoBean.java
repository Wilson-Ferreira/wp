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
public class AlterarPedidoBean implements Serializable {

    @Inject
    private ListarPedidoBean listarPedidoBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private PedidoService pedidoService;
   
    
    public void preparaAlterarPedido() {

        if (listarPedidoBean.verificaSeHaMaisDeUmPedidoSelecionadoNaLista()) {

            return;
        }

        try {

            listarPedidoBean.buscarItemCardapioPorCategoria(null);

            listarPedidoBean.buscarMesasFuncionariosQuantidadesCategoriasCartoes();

            jsfUtil.alterar();

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

    public void alterarPedido() {

        try {

            pedidoService.salvarAlterarPedido(listarPedidoBean.getPedido());

            jsfUtil.addMensagemInfo("Pedido alterado com sucesso");
            
            jsfUtil.listar();
            
            listarPedidoBean.getListaPedidosSelecionados().clear();

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
