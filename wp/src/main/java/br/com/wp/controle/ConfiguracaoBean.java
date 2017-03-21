package br.com.wp.controle;

import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cidade;
import br.com.wp.modelo.Configuracao;
import br.com.wp.modelo.Uf;
import br.com.wp.service.CidadeService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.UfService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
@Named(value = "configuracaoBean")
@ViewScoped
public class ConfiguracaoBean implements Serializable {

    @Inject
    private Configuracao configuracao;
    @Inject
    private CidadeService cidadeService;
    @Inject
    private UfService ufService;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private ConfiguracaoService configuracaoService;

    private List<Cidade> listaCidades;
    private List<Uf> listaUfs;

    public void cancelar() {

        jsfUtil.listar();
    }

    public void buscarConfiguracoes() {

        try {

            configuracao = configuracaoService.buscarConfiguracoes();
            
            if(configuracao == null){
                
                jsfUtil.addMensagemInfo("Não há dados cadastrados");
            }

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }

    public void buscarCidades() {

        try {

            listaCidades = cidadeService.buscarTodasCidades();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }

    public void buscarUfs() {

        try {

            listaUfs = ufService.buscarTodasUfs();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }

    public void buscarCidadesPorUf(AjaxBehaviorEvent event) {

        try {

            listaCidades = cidadeService.buscarCidadesPorUf(configuracao.getUf());

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }

    public void preparaEditarConfiguracoes() {

        buscarCidades();
        jsfUtil.alterar();
    }

    public void preparaSalvarConfiguracoes() {
        
        configuracao = new Configuracao();
        
        jsfUtil.salvar();
    }

    public void salvarAlterarConfiguracoes() {

        try {

            configuracaoService.salvarAlterarConfiguracoes(configuracao);
            
            if (configuracao.getId() == null) {
                
                jsfUtil.addMensagemInfo("Configuração salva com sucesso");
                
            } else {
                
                jsfUtil.addMensagemInfo("Configuração alterada com sucesso");
            }
            
            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
                
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
            }
        }
    }

    public void preparaExcluirConfiguracoes() {

        jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirConfiguracoes() {

        try {

            configuracaoService.excluirConfiguracoes(configuracao);
            
            jsfUtil.addMensagemInfo("Configuração excluida com sucesso");

            jsfUtil.listar();
            configuracao = null;
            
            
        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
                
            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
            }
        }
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public List<Cidade> getListaCidades() {
        return listaCidades;
    }

    public List<Uf> getListaUfs() {
        return listaUfs;
    }
}
