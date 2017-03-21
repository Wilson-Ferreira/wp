/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;

/**
 *
 * @author User
 */
@FacesValidator(value = "validaValor")
public class ValidaValor implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) {

        if (value == null || value.toString().equals("0.00")) {
            ((UIInput) uic).setValid(false);
            FacesContext.getCurrentInstance().addMessage
        (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor é obrigatório", ""));
        }
    }
}
