/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.modelo.Contrato;
import br.com.wp.modelo.Usuario;
import br.com.wp.service.ContratoService;
import br.com.wp.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wilson F Florindo
 */
@Named
@ViewScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    @Inject
    private Usuario usuarioLogado;
    @Inject
    private ContratoService contratoService;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private Contrato contrato;

    public void buscarUsuarioNaSessao() {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
       
    }

    public String autenticarUsuario() throws ServletException, IOException {

        contrato = contratoService.vericaVencimentoContrato();

        System.out.println("dataLogin "+contrato.getStrDataVencContrato());
        if (contrato.getIntTempoContrato() <= 0) {
            
            jsfUtil.addMensagemInfo("Contrato com a Wp-Sistemas expirou em "+contrato.getStrDataVencContrato());
            return "controle_contrato";

        } else {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                    .getRequestDispatcher("/j_spring_security_check?j_login=" + getUsername() + "&j_senha=" + getPassword()+"&j_data=" + contrato.getStrDataVencContrato());
            dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
            FacesContext.getCurrentInstance().responseComplete();

            return null;

        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

}
