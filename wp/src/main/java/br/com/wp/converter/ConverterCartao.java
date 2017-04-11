/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Cartao;
import br.com.wp.service.CartaoService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@FacesConverter(value = "converterCartao")
public class ConverterCartao implements Converter {

    @Inject
    private CartaoService cartaoService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

    
        if (string != null && !string.equals("Selecione") && !string.equals("")) {

            try {
                return cartaoService.buscarCartaoPorId(Long.parseLong(string));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
          if(value != null && !value.equals("")){
            return String.valueOf(((Cartao)value ).getId());
        }
        return null;
    }
}
