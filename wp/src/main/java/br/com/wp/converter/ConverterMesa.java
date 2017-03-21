/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Mesa;
import br.com.wp.modelo.Uf;
import br.com.wp.service.MesaService;
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
@FacesConverter(value = "converterMesa")
public class ConverterMesa implements Converter {

    @Inject
    private MesaService mesaService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

     System.out.println("converter "+string);
        if(string != null && !string.equalsIgnoreCase("Selecione")&& !string.equals("")){
          
            try {
                return mesaService.buscarMesaPorId(Long.parseLong(string));
            } catch (Exception ex) {
                Logger.getLogger(ConverterMesa.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
       // System.out.println("converter "+String.valueOf(((Uf)value).getId()));
        if(value instanceof Mesa && value != null){  
            Mesa mesa = (Mesa) value;  
            return mesa.getId().toString();  
        }  
        return null;
    }
}
