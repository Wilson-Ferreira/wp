/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Cargo;
import br.com.wp.service.CargoService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@FacesConverter(value = "converterCargo")
public class CargoConverter implements Converter {

    @Inject
    private CargoService cargoService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

    
        if (string != null && !string.equals("Selecione") && !string.equals("")) {

            try {
                return cargoService.buscarCargoPorId(Long.parseLong(string));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
         if(value instanceof Cargo && value != null){  
            Cargo cargo = (Cargo) value;  
            return cargo.getId().toString();  
        }  
        return null;
    }
}
