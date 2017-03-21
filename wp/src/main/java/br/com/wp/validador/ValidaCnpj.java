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
 * @author Wilson F Florindo
 */
@FacesValidator(value="validaCnpj")
public class ValidaCnpj implements Validator{
    
     @Override
    public void validate(FacesContext context, UIComponent component, Object value){
        
        if (value.toString().equals("")) {
            ((UIInput) component).setValid(false);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cnpj é obrigatário!",""));
       
        } else {
            
             String cnpj = value.toString().replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/","");
             
            if (!validaCNPJ(String.valueOf(cnpj))) {
                ((UIInput) component).setValid(false);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cnpj é inválido!",""));
            }
        }
    }

    public static boolean validaCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return false;
        }

        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException e) { // CNPJ não possui somente números
            return false;
        }

        int soma = 0;
        String cnpj_calc = cnpj.substring(0, 12);

        char chr_cnpj[] = cnpj.toCharArray();
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }

        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }

        int dig = 11 - soma % 11;
        cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }

        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - soma % 11;
        cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

        return cnpj.equals(cnpj_calc);
    }
}


