/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Quantidade;
import br.com.wp.service.QuantidadeService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@FacesConverter(value = "converterQuantidade")
public class QuantidadeConverter implements Converter {

    @Inject
    private QuantidadeService quantidadeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

    
        if (string != null && !string.equals("Selecione") && !string.equals("")) {

            try {
                return quantidadeService.buscarQuantidadePorId(Long.parseLong(string));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
          if(value != null && !value.equals("")){
            return String.valueOf(((Quantidade)value ).getId());
        }
        return null;
    }
}
