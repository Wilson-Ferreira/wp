/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 * Classe responsável pelo controle da renderização dos botões salvar e alterar
 * e outros componentes dos formulários nas páginas do sistema.
 *
 * @author Wilson Ferreira Florindo
 */
@Named
@ViewScoped
public class JsfUtil implements Serializable {

    private boolean formSalvarAlterar;
    private boolean botaoSalvar;
    private boolean botaoAlterar;
    private boolean camposSalvar;
    private boolean camposAlterar;
    private boolean botaoTranferirPedidos;
    private boolean dataTable = true;
    private boolean toolbar = true;
    private boolean pageTitle = true;
    private boolean campoCartao = false;
    private boolean campoPickList = false;

    public void preparaEditarAutorizacoes() {
        setFormSalvarAlterar(true);
        setDataTable(false);
        setToolbar(false);
        setBotaoAlterar(false);
        setBotaoSalvar(false);
        setCampoPickList(true);
        setBotaoTranferirPedidos(false);
        setCamposAlterar(false);
        setCamposSalvar(false);
        setPageTitle(false);
    }

    public void listar() {
        setFormSalvarAlterar(false);
        setDataTable(true);
        setToolbar(true);
        setBotaoAlterar(false);
        setBotaoSalvar(false);
        setCampoPickList(false);
        setPageTitle(true);
        setBotaoTranferirPedidos(false);
    }

    public void salvar() {
        setFormSalvarAlterar(true);
        setDataTable(false);
        setToolbar(false);
        setBotaoAlterar(false);
        setBotaoSalvar(true);
        setCamposAlterar(false);
        setCamposSalvar(true);
        setPageTitle(false);
        setBotaoTranferirPedidos(false);
    }

    public void alterar() {
        setFormSalvarAlterar(true);
        setDataTable(false);
        setToolbar(false);
        setBotaoAlterar(true);
        setBotaoSalvar(false);
        setCamposSalvar(false);
        setCamposAlterar(true);
        setPageTitle(false);
        setBotaoTranferirPedidos(false);
    }

    public void preparaTranferirPedidos() {
        setFormSalvarAlterar(true);
        setDataTable(false);
        setToolbar(false);
        setBotaoAlterar(false);
        setBotaoSalvar(false);
        setPageTitle(false);
        setBotaoTranferirPedidos(true);
    }

    public void adicionarObjectNoScopedFlash(String aux, int mesaCartao) {

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().put("mesaCartao", mesaCartao); // incluindo um objeto no escopo Flash
    }

    public void adicionarMensagemNoScopedFlash() {

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void abrirFecharDialog(String dialog) {
        RequestContext.getCurrentInstance().execute(dialog);
    }

    public void addMensagemInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
    }

    public void addMensagemErro(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
    }

    public boolean isFormSalvarAlterar() {
        return formSalvarAlterar;
    }

    public void setFormSalvarAlterar(boolean formSalvarAlterar) {
        this.formSalvarAlterar = formSalvarAlterar;
    }

    public boolean isBotaoSalvar() {
        return botaoSalvar;
    }

    public void setBotaoSalvar(boolean botaoSalvar) {
        this.botaoSalvar = botaoSalvar;
    }

    public boolean isBotaoAlterar() {
        return botaoAlterar;
    }

    public void setBotaoAlterar(boolean botaoAlterar) {
        this.botaoAlterar = botaoAlterar;
    }

    public boolean isDataTable() {
        return dataTable;
    }

    public void setDataTable(boolean dataTable) {
        this.dataTable = dataTable;
    }

    public boolean isToolbar() {
        return toolbar;
    }

    public void setToolbar(boolean toolbar) {
        this.toolbar = toolbar;
    }

    public boolean isPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(boolean pageTitle) {
        this.pageTitle = pageTitle;
    }

    public boolean isCampoCartao() {
        return campoCartao;
    }

    public void setCampoCartao(boolean campoCartao) {
        this.campoCartao = campoCartao;
    }

    public boolean isBotaoTranferirPedidos() {
        return botaoTranferirPedidos;
    }

    public void setBotaoTranferirPedidos(boolean botaoTranferirPedidos) {
        this.botaoTranferirPedidos = botaoTranferirPedidos;
    }

    public boolean isCampoPickList() {
        return campoPickList;
    }

    public void setCampoPickList(boolean campoPickList) {
        this.campoPickList = campoPickList;
    }

    public boolean isCamposSalvar() {
        return camposSalvar;
    }

    public void setCamposSalvar(boolean camposSalvar) {
        this.camposSalvar = camposSalvar;
    }

    public boolean isCamposAlterar() {
        return camposAlterar;
    }

    public void setCamposAlterar(boolean camposAlterar) {
        this.camposAlterar = camposAlterar;
    }

}
