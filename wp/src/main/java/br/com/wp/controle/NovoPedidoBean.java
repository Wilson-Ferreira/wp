/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.enumeracao.NumeroCartao;
import br.com.wp.enumeracao.QuantidadeUm;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cardapio;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Mesa;
import br.com.wp.modelo.Pedido;
import br.com.wp.modelo.Quantidade;
import br.com.wp.enumeracao.StatusPedido;
import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.CartaoService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.PedidoService;
import br.com.wp.service.QuantidadeService;
import br.com.wp.util.JsfUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wilson F Florindo
 */

@Named(value = "novoPedidoBean")
@ConversationScoped
public class NovoPedidoBean implements Serializable {

    @Inject
    private CardapioBean cardapioBean;
    @Inject
    private CartaoService cartaoService;
    @Inject
    private NovoPedidoMesaBean novoPedidoMesaBean;
    @Inject
    private NovoPedidoCartaoBean novoPedidoCartaoBean;
    @Inject
    private NovoPedidoFuncionarioBean novoPedidoFuncionarioBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private PedidoService pedidoService;
    @Inject
    private Pedido pedido;
    @Inject
    private Cartao cartao;
    @Inject
    private QuantidadeService quantidadeService;

    private List<Pedido> listaPedidos;
    private List<Mesa> listaMesas;
    private List<Funcionario> listaFuncionarios;
    private List<Quantidade> listaQuantidade;
    private String tipoDeCobranca;

 
    public void instanciarPedido() {

        pedido = new Pedido();
    }

    public String voltar(){
        
        if(tipoDeCobranca.equalsIgnoreCase(TipoCobranca.CARTÃO.toString())){
            
            return "novo_pedido_cartao";
            
        }else{
            
            return "novo_pedido_mesa";
        }
    }
    
    public String inicializar(){
        
         String aux = "";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        for (UsuarioAutorizacao ua : usuarioLogado.getUsuarioAutorizacao()) {

            if (ua.getId().getAutorizacao().getNomeFantazia().equals("SALVAR PEDIDOS")) {

                aux = "1";
            }
        }
        
        if(aux.equals("1")){
            
            criarPedido();
            return "novo_pedido";
            
        }else{
            
            return "/pagina_negada.xhtml";
        }
    }
   
    public void criarPedido() {

        try {

            tipoDeCobranca = configuracaoService.buscarTipoDeCobranca();
          
            buscarQuantidades();
           
            jsfUtil.listar();
           
            Quantidade quantidade = quantidadeService.buscarQuantidadeUm(QuantidadeUm.UM);

            listaPedidos = new ArrayList<>();

            for (Cardapio cardapio : cardapioBean.getListaItensSelecionados()) {

                pedido.setItemCardapio(cardapio);
                pedido.setStatusPedido(StatusPedido.ABERTO);

                pedido.setQuantidade(quantidade);

                listaPedidos.add(pedido);
                pedido = new Pedido();
            }

            instanciarPedido();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
                
            } else {
                
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
            }
        }
    }

    public String salvarPedido() {

        Date date = new Date();

        listaPedidos.stream().forEach((p) -> {

            try {

                Pedido ped = new Pedido();

                if (novoPedidoCartaoBean.getCartao().getNumeroCartao() == null) {
                
                    cartao = cartaoService.buscarCartaoNumeroZero(NumeroCartao.ZERO.numero());
                    ped.setCartao(cartao);
                 
                } else {
                    
                    ped.setCartao(novoPedidoCartaoBean.getCartao());
                }

                if (p.getId() == null) {
                    ped.setStatusPedido(StatusPedido.ABERTO);
                    ped.setDataPedido(date);
                }

                ped.setItemCardapio(p.getItemCardapio());
                ped.setQuantidade(p.getQuantidade());
                ped.setFuncionario(novoPedidoFuncionarioBean.getFuncionario());
                ped.setMesa(novoPedidoMesaBean.getMesa());
                pedidoService.salvarAlterarPedido(ped);

            } catch (Exception ex) {

                UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {
                    
                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
                    
                } else {
                    
                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
                }
            }
        });

        jsfUtil.addMensagemInfo("Pedido salvo com sucesso!");
        jsfUtil.adicionarMensagemNoScopedFlash();
        
        cardapioBean.encerrarConversation();
        
        return "pedidos?faces-redirect=true";
    }

    public String cancelarNovoPedido() {

        cardapioBean.encerrarConversation();
        return "pedidos?faces-redirect=true";
    }
 
    public void buscarQuantidades() {

        try {

            listaQuantidade = quantidadeService.buscarQuantidade();

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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public List<Mesa> getListaMesas() {
        return listaMesas;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public List<Quantidade> getListaQuantidade() {
        return listaQuantidade;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public String getTipoDeCobranca() {
        return tipoDeCobranca;
    }
    

}
