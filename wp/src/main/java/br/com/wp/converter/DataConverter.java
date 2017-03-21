/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.converter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author User
 */
@FacesConverter(value = "dataConverter")
public class DataConverter implements Converter {

   String campo;
    String msgInvalido = null;
    boolean ano = false;
    String[] data;
    Calendar c = Calendar.getInstance();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("Executou converterData "+value);
       
        if (!value.equals("") && value != null) {
            System.out.println("Executou converterData dentro do if getAsObject");
            try {
                data = value.split("/");
                c = Calendar.getInstance();
                c.set(Integer.parseInt(data[2]), Integer.parseInt(data[1]) - 1, Integer.parseInt(data[0]));
                return c.getTime();

            } catch (Exception e) {
                return null;
            }
        } else {
            System.out.println("Executou converterData dentro do else null getAsObject");
            return value;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (!value.equals("") && value != null) {
            System.out.println("Executou converterData dentro do if getAsString "+value.toString());
            java.util.Date date = ((java.util.Date) value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Format formato = new SimpleDateFormat("dd/MM/yyyy");
            String retorno = formato.format(calendar.getTime());

            return retorno;

        } else {
            System.out.println("Executou converterData dentro do null getAsString");
            return value.toString();
        }
    }
}
