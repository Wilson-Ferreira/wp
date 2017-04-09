/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.modelo.Contrato;
import br.com.wp.modelo.Usuario;
import br.com.wp.service.ContratoService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    public void cancelar() {

    }

    public String validarSistema() {

        try {

            Contrato contratoNovo = contratoService.buscarDadosContrato();
           
            contratoNovo.setChave_data(contrato.getChave_data());
            contratoNovo.setChave_tempo(contrato.getChave_tempo());

            contratoService.validarSistema(contratoNovo);
            contrato = new Contrato();

            return "login";

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
