/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Uf;
import br.com.wp.service.UfService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
@FacesConverter(value = "converterUf")
public class ConverterUf implements Converter {

    @Inject
    private UfService ufService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

     System.out.println("converter "+string);
        if(string != null && !string.equalsIgnoreCase("Selecione")&& !string.equals("")){
          
            try {
                return ufService.buscarUfPorId(Long.parseLong(string));
            } catch (Exception ex) {
                Logger.getLogger(ConverterUf.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
       // System.out.println("converter "+String.valueOf(((Uf)value).getId()));
        if(value instanceof Uf && value != null){  
            Uf uf = (Uf) value;  
            return uf.getId().toString();  
        }  
        return null;
    }
}
