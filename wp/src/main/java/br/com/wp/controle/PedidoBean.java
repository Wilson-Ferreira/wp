package br.com.wp.controle;

import br.com.wp.enumeracao.NomeCategoria;
import br.com.wp.enumeracao.NomeSecao;
import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cardapio;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Categoria;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Mesa;
import br.com.wp.modelo.Pedido;
import br.com.wp.modelo.Quantidade;
import br.com.wp.enumeracao.StatusPedido;
import br.com.wp.enumeracao.TipoCargo;
import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.service.CardapioService;
import br.com.wp.service.CartaoService;
import br.com.wp.service.CategoriaService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.FuncionarioService;
import br.com.wp.service.MesaService;
import br.com.wp.service.PedidoService;
import br.com.wp.service.QuantidadeService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Wilson F Florindo
 */
@Named(value = "pedidoBean")
@ConversationScoped
public class PedidoBean implements Serializable {

    @Inject
    Conversation conversation;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private CartaoService cartaoService;
    @Inject
    private CategoriaService categoriaService;
    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private MesaService mesaService;
    @Inject
    private QuantidadeService quantidadeService;
    @Inject
    private CardapioService cardapioService;
    @Inject
    private PedidoService pedidoService;
    @Inject
    private Pedido pedido;
    @Inject
    private Cartao cartao;
    @Inject
    private Cardapio itemCardapio;

    @Inject
    private Categoria categoria;
    @Inject
    private Mesa mesa;
    @Inject
    private Quantidade quantidade;

    private List<Pedido> listaPedidos;
    private List<Pedido> listaPedidosSelecionados;
    private List<Funcionario> listaFuncionarios;
    private List<Categoria> listaCategorias;
    private List<Cardapio> listaItensCardapio;
    private List<Quantidade> listaQuantidade;
    private List<Mesa> listaMesas;
    private List<Cartao> listaCartoesRetidos;
  
    private String secao;
    private String tipoDeCobranca;
   
    public void iniciarConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void encerrarConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public void onRowSelect(SelectEvent event) {

        listaPedidosSelecionados.stream().forEach((pedidoDaLista) -> {
            pedido = pedidoDaLista;
        });
    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public void inicializar(ActionEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        secao = context.getExternalContext().getRequestParameterMap().get("secao");

        buscarTodosPedidos();
    }

    public void buscarTipoDeCobranca() {

        try {

            tipoDeCobranca = configuracaoService.buscarTipoDeCobranca();

            if (tipoDeCobranca.equalsIgnoreCase(TipoCobranca.CARTÃO.toString())) {
                jsfUtil.setCampoCartao(true);
            }

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex.getMessage());
            }
        }
    }

    public void buscarTodosPedidos() {

        try {

            listaPedidos = new ArrayList<>();

            if (secao == null || secao.equals(NomeSecao.caixa.toString())) {

                setSecao(NomeSecao.caixa.toString());
                listaPedidos = pedidoService.buscarPedidosNaoPagos(StatusPedido.PAGO);

            } else {

                listaPedidos = pedidoService.buscarPedidosNaoPagosPorSecao(StatusPedido.PAGO, StatusPedido.PROCESSANDO, secao);
            }

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

    public void buscarMesasFuncionariosQuantidadesCategoriasCartoes() {

        try {

            if(tipoDeCobranca.equalsIgnoreCase(TipoCobranca.CARTÃO.toString())){
                
            listaCartoesRetidos = cartaoService.buscarCartoesRetidos(StatusCartao.RETIDO);
            }
            
            listaMesas = mesaService.buscarTodasMesas();
            listaFuncionarios = funcionarioService.buscarFuncionarioPorCargo(TipoCargo.GARÇÕN.toString());
            listaCategorias = categoriaService.buscarCategorias(NomeCategoria.TODAS);
            listaQuantidade = quantidadeService.buscarQuantidade();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("todos Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
            }
        }
    }

    public void instanciarPedidos() {

        listaPedidosSelecionados = null;
        pedido = new Pedido();
        cartao = new Cartao();
        quantidade = new Quantidade();
        mesa = new Mesa();
        categoria = new Categoria();
        itemCardapio = new Cardapio();
    }

    public void cancelar() {

        instanciarPedidos();
        jsfUtil.listar();
    }

    public void buscarItemCardapioPorCategoria(AjaxBehaviorEvent event) {

        try {

            listaItensCardapio = new ArrayList<>();

            listaItensCardapio = cardapioService.buscarItemCardapioPorCategoria(pedido.getItemCardapio().getCategoria().getId());

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

    public boolean verificaSeHaMaisDeUmPedidoSelecionadoNaLista() {

        if (listaPedidosSelecionados == null || listaPedidosSelecionados.size() > 1) {
            jsfUtil.addMensagemInfo("Selecione apenas um pedido");

            return true;

        }
             return false;
      
    }

    public void alterarStatusPedido() {

        try {

            pedidoService.alterarStatusPedido(listaPedidosSelecionados);

            jsfUtil.addMensagemInfo("Status alterado com sucesso");

            if (!secao.equalsIgnoreCase("caixa")) {

                listaPedidos.removeAll(listaPedidosSelecionados);
            }

            instanciarPedidos();

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

    public String preparaCalcularConta() {

        return "calculo_contas?faces-redirect=true;";
    }

    public String iniciarNovoPedido() {

        return "cardapio?faces-redirect=true";
    }

    public void preparaAlterarPedido() {

        if (verificaSeHaMaisDeUmPedidoSelecionadoNaLista()) {

            return;
        }

        try {

            listaItensCardapio = cardapioService.buscarItemCardapioPorCategoria(pedido.getItemCardapio().getCategoria().getId());

            buscarMesasFuncionariosQuantidadesCategoriasCartoes();

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

            pedidoService.salvarAlterarPedido(pedido);

            jsfUtil.addMensagemInfo("Pedido alterado com sucesso");
            jsfUtil.listar();
            listaPedidosSelecionados.clear();

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

    public void preparaExcluirPedido() {

        jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirPedido() {

        try {

            pedidoService.excluirPedidos(listaPedidosSelecionados);

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex.getMessage());
            }
        }

        listaPedidos.removeAll(listaPedidosSelecionados);
        instanciarPedidos();
        jsfUtil.addMensagemInfo("Pedidos excluidos com sucesso");
    }

    public void preparaTransferirPedidos() {

        if (!verificaSeHaMaisDeUmPedidoSelecionadoNaLista()) {

            try {
                
                buscarMesasFuncionariosQuantidadesCategoriasCartoes();
                
                 listaPedidosSelecionados.stream().forEach((ped) -> {               
                 listaCartoesRetidos.add(ped.getCartao());
            });
                 
                listaItensCardapio = cardapioService.buscarItemCardapioPorCategoria(pedido.getItemCardapio().getCategoria().getId());

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

    public void tranferirPedidos() {

        try {

            pedidoService.transferirPedidos(pedido.getCartao(), cartao);

            buscarTodosPedidos();
            jsfUtil.addMensagemInfo("Pedidos tranferidos com sucesso");

            listaPedidosSelecionados = null;
            instanciarPedidos();
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

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public List<Cardapio> getListaItensCardapio() {
        return listaItensCardapio;
    }

    public List<Quantidade> getListaQuantidade() {
        return listaQuantidade;
    }

    public List<Mesa> getListaMesas() {
        return listaMesas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Quantidade quantidade) {
        this.quantidade = quantidade;
    }

    public List<Pedido> getListaPedidosSelecionados() {
        return listaPedidosSelecionados;
    }

    public void setListaPedidosSelecionados(List<Pedido> listaPedidosSelecionados) {
        this.listaPedidosSelecionados = listaPedidosSelecionados;
    }

    public Cardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(Cardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public List<Cartao> getListaCartoesRetidos() {
        return listaCartoesRetidos;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public String getTipoDeCobranca() {
        return tipoDeCobranca;
    }

}
