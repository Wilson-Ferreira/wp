/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Autorizacao;
import br.com.wp.service.AutorizacaoService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
@FacesConverter(value="converterAutorizacao")
public class ConverterAutorizacao implements Converter{

    @Inject
    private AutorizacaoService autorizacaoService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        
         if(value.equals("Selecione")){
            ((UIInput) component).setValid(false);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo autorização é obrigatório!",""));
        }
    
        if (value != null && !value.equals("Selecione") && !value.equals("")) {
          
           
            try {
                return autorizacaoService.buscarAutorizacaoPorId(Long.parseLong(value));
            } catch (Exception ex) {
                Logger.getLogger(ConverterAutorizacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        }
    
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        
         if (!value.equals("")) {
            return String.valueOf(((Autorizacao) value).getId());
        }
        return null;
    }
}
