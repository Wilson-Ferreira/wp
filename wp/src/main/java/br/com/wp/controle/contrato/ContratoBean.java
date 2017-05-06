/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.contrato;

import br.com.wp.modelo.Contrato;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.ContratoService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wilson F Florindo
 */
@Named
@ViewScoped
public class ContratoBean implements Serializable {

    @Inject
    private Contrato contrato;
    @Inject
    private Usuario usuario;
    @Inject
    private ContratoService contratoService;

    @Inject
    private JsfUtil jsfUtil;
    
    public void cancelar() {

    }

    public boolean verificarAutorizacao(){
          FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        usuario = (Usuario) session.getAttribute("usuarioLogado");
        
/*        for(UsuarioAutorizacao aut : usuario.getUsuarioAutorizacao()){
            if(aut.getId().getAutorizacao().getTipo().equalsIgnoreCase("ROLE_ADMIN")){
            */   
                return true;
              /*
            }
        }
        return false;*/
    }
    
    public String validarSistema() {

        try {

            if(verificarAutorizacao()){
                
            contratoService.validarSistema(contrato.getChave());

            contrato = new Contrato();
            
            return "login";
            
            }else{
                
               jsfUtil.addMensagemInfo("Você não tem permissão para executar esta operação");
            }

        } catch (Exception ex) {

            Logger.getLogger(ContratoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;

    }
    
    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
