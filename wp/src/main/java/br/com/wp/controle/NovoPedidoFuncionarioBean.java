/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.enumeracao.TipoCargo;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.FuncionarioService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ConversationScoped
public class NovoPedidoFuncionarioBean implements Serializable {

    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private Funcionario funcionario;
    @Inject
    private JsfUtil jsfUtil;
  
    private List<Funcionario> listaFuncionarios;
  
    public String buscarTodosFuncionariosGarcons() {

        String aux = "";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
       
        for (UsuarioAutorizacao ua : usuarioLogado.getUsuarioAutorizacao()) {
         
            if (ua.getId().getAutorizacao().getNomeFantazia().equals("SALVAR PEDIDOS")) {
             
                aux="1";
            }
        }
        
         if(aux.equals("1")){
           
                try {
                    
                    listaFuncionarios = funcionarioService.buscarFuncionarioPorCargo(TipoCargo.GARÇÕN.toString());
                    
                } catch (Exception ex) {
                    
                   UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {

                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                } else {

                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
                }
                } 
                
                return "novo_pedido_funcionario";
                
            }else{
             
              return "/pagina_negada.xhtml";
        }
    }

    public String selecionarFuncionario() {

        return "novo_pedido_mesa?faces-redirect=true;";
    }

    public String voltar() {
        return "cardapio";
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }
}
