/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.novopedido;

import br.com.wp.controle.cliente.ListarClienteBean;
import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Configuracao;
import br.com.wp.modelo.Mesa;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.MesaService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class NovoPedidoMesaBean implements Serializable {

    @Inject
    private Mesa mesa;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private Configuracao configuracao;
    @Inject
    private MesaService mesaService;
    @Inject
    private ConfiguracaoService configuracaoService;
    private List<Mesa> listaMesas;

    private String tipoDeCobranca;

    public void buscarTipoDeCobranca() {

        try {

            tipoDeCobranca = configuracaoService.buscarTipoDeCobranca();

        } catch (Exception ex) {

            Logger.getLogger(ListarClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String buscarTodasMesas() {

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

                configuracao = configuracaoService.buscarConfiguracoes();
                listaMesas = mesaService.buscarTodasMesas();

            } catch (Exception ex) {

                UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {

                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                } else {

                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
                }
            }

            return "novo_pedido_mesa";

        } else {

            return "/pagina_negada.xhtml";
        }
    }

    public String selecionarMesa() {

        if (configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.CARTÃO.toString())) {

            jsfUtil.setCampoCartao(true);

            return "novo_pedido_cartao?faces-redirect=true;";

        } else {

            return "novo_pedido?faces-redirect=true;";
        }
    }

    public String voltar() {
        return "novo_pedido_funcionario";
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<Mesa> getListaMesas() {
        return listaMesas;
    }

    public String getTipoDeCobranca() {
        return tipoDeCobranca;
    }

}
