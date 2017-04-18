/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.CartaoService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ConversationScoped
public class NovoPedidoCartaoBean implements Serializable {

    @Inject
    private Cartao cartao;
    @Inject
    private CartaoService cartaoService;
    @Inject
    JsfUtil jsfUtil;
    private List<Cartao> listaCartoesRetidos;

    public String buscarCartoesRetidos() {

        String aux = "";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        for (UsuarioAutorizacao ua : usuarioLogado.getUsuarioAutorizacao()) {

            if (ua.getId().getAutorizacao().getNomeFantazia().equals("ADICIONAR PEDIDOS")) {

                aux = "1";
            }
        }

        if (aux.equals("1")) {

            try {

                listaCartoesRetidos = cartaoService.buscarCartoesRetidos(StatusCartao.RETIDO);

            } catch (Exception ex) {

                UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {

                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                } else {

                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
                }
            }

            return "novo_pedido_cartao";

        } else {

            return "/pagina_negada.xhtml";
        }
    }

    public String selecionarCartao() {
        System.out.println("cartao " + cartao.getNumeroCartao());
        return "novo_pedido?faces-redirect=true;";
    }

    public String voltar() {
        return "novo_pedido_mesa";
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public List<Cartao> getListaCartoesRetidos() {
        return listaCartoesRetidos;
    }
}
