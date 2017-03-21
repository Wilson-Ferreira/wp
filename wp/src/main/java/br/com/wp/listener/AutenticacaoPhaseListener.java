/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.listener;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
public class AutenticacaoPhaseListener implements PhaseListener{
    
     @Override
    public void afterPhase(PhaseEvent event) {
        //não implementado
    }
 
    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            String mensagem = (String) session.getAttribute("msg");
 
            if (mensagem != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null));
                session.setAttribute("msg", null);
            }
        }
    }
 
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
