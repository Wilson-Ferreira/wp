/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Cidade;
import br.com.wp.service.CidadeService;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@FacesConverter(value = "converterCidade")
public class ConverterCidade implements Converter {

    @Inject
    private CidadeService cidadeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

      System.out.println("converter cidade "+string);
        if (string != null && !string.equals("Selecione") && !string.equals("")) {

            try {
                return cidadeService.buscarCidadePorId(Long.parseLong(string));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
         if(value instanceof Cidade && value != null){  
            Cidade cidade = (Cidade) value;  
            return cidade.getId().toString();  
        }  
        return null;
    }
}
