/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import br.com.wp.modelo.Funcionario;
import br.com.wp.service.FuncionarioService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author User
 */
@FacesConverter(value="converterFuncionario")
public class ConverterFuncionario implements Converter {

    @Inject
    private FuncionarioService funcionarioService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
   
      if(string != null && !string.equals("Selecione")&& !string.equals("")){
        
              try {
                  return funcionarioService.buscarFuncionarioPorId(Long.parseLong(string));
              } catch (Exception ex) {
                  Logger.getLogger(ConverterFuncionario.class.getName()).log(Level.SEVERE, null, ex);
              }
      }
        return null;
    }
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
           if(o != null && !o.equals("")){
            return String.valueOf(((Funcionario)o ).getId());
        }
        return null;
    }
}
