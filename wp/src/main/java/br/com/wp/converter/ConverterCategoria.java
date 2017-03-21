/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Categoria;
import br.com.wp.service.CategoriaService;
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
@FacesConverter(value = "converterCategoria")
public class ConverterCategoria implements Converter {

   @Inject
   private CategoriaService categoriaService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

    
        if (string != null && !string.equals("Selecione") && !string.equals("")) {

            try {
                return categoriaService.buscarCategoriaPorId(Long.parseLong(string));
            } catch (Exception ex) {
                Logger.getLogger(ConverterCategoria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
         if (!value.equals("") && value != null) {
            return String.valueOf(((Categoria) value).getId());
        }
        return null;
    
    }
}
