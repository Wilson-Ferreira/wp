/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.controle.pedido.ListarPedidoBean;
import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cliente;
import br.com.wp.modelo.Configuracao;
import br.com.wp.modelo.Pedido;
import br.com.wp.service.ClienteService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.service.PedidoService;
import br.com.wp.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ConversationScoped
public class CalculaContaBean implements Serializable {
    
    @Inject
    private Configuracao configuracao;
    @Inject
    private ClienteService clienteService;
    @Inject
    private PedidoService pedidoService;
    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private Pedido pedido;
    @Inject
    private ListarPedidoBean pedidoBean;
    @Inject
    private JsfUtil jsfUtil;
    
    private List<Pedido> listaPedidosPagar;
    private BigDecimal porcentagemDoServico;
    private BigDecimal subTotal;
    private BigDecimal totalConta;
    private BigDecimal entrada_couvert;
    private int quantidadeEntradaCouvert;
    private boolean cobrarPorcentagemDoServico = true;
    private boolean cobrarEntradaCouvert;
    private JasperPrint jasperPrint;
    
    public void buscarConfiguracoes() {
        
        try {
            
            configuracao = configuracaoService.buscarConfiguracoes();
            
            if (configuracao.getEntrada_couvert().intValue() > 0) {
                setCobrarEntradaCouvert(true);
            }
            
            if (configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.CARTÃO.toString())) {
                jsfUtil.setCampoCartao(true);
                
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
    
    public void criarPedidosParaExibir() {
        
        try {
            
            //buscarConfiguracoes();
            
            listaPedidosPagar = new ArrayList<>();
            
            listaPedidosPagar.addAll(pedidoBean.getListaPedidosSelecionados());
            
            calcularSubTotal();
            
        } catch (Exception ex) {
            
            jsfUtil.addMensagemErro("Ocorreu um erro ao criar os pedidos");
        }
    }
    
    public void calcularSubTotal() {
        
        try {
            
            subTotal = new BigDecimal("00.00").setScale(2, RoundingMode.UP);
            
            listaPedidosPagar.stream().forEach((pedidosParaCalculo) -> {
                
                subTotal = subTotal.add(pedidosParaCalculo.getItemCardapio().getValor().multiply(pedidosParaCalculo.getQuantidade().getQuantidade()));
            });
            
        } catch (Exception ex) {
            
            jsfUtil.addMensagemErro("Ocorreu um erro ao calcular o sub total");
        }
    }
    
    public String finalizarPagamentoConta() {
        
        try {
            
            pedidoService.alterarStatusParaPago(listaPedidosPagar);
            
            if(configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.CARTÃO.toString())){
            liberarCartoesPagos();
            }
            
            jsfUtil.addMensagemInfo("Cálculo finalizado com sucesso");
            jsfUtil.adicionarMensagemNoScopedFlash(); 
            
            pedidoBean.encerrarConversation();
            
        } catch (Exception ex) {
            
            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);
            
            if (th instanceof SQLException) {
                
                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());
                
            } else {
                
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!"+ex.getMessage());
            }
        }
       
        return "pedidos?faces-redirect=true;";
        
    }
    
    public void liberarCartoesPagos() {
        
        try {
            
            for (Pedido p : listaPedidosPagar) {
                
                Cliente cliente = clienteService.buscarClientePorCartao(p.getCartao());
                cliente.setStatusCartao(StatusCartao.LIBERADO.toString());
                
                clienteService.salvarAlterarCliente(cliente);
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
    
    public String cancelarCalculo() {
        
        jsfUtil.addMensagemInfo("Cálculo cancelado");
        
        jsfUtil.adicionarMensagemNoScopedFlash();
        pedidoBean.encerrarConversation();
        
        return "pedidos?faces-redirect=true";
        
    }
    
    public void calcularConta() {
        
        try {
            
            totalConta = new BigDecimal("00.00").setScale(2, RoundingMode.UP);
            porcentagemDoServico = new BigDecimal("00.00").setScale(2, RoundingMode.UP);
            entrada_couvert = new BigDecimal("00.00").setScale(2, RoundingMode.UP);
            
            if (configuracao.getPorcentagemServico() > 0 && cobrarPorcentagemDoServico) {
                calcularPorcentagemServico();
            }
            
            if (configuracao.getEntrada_couvert().intValue() > 0 && cobrarEntradaCouvert) {
                calcularEntradaCouvert();
            }
            
            totalConta = totalConta.add(subTotal);
            totalConta = totalConta.add(porcentagemDoServico);
            totalConta = totalConta.add(entrada_couvert);
            
            jsfUtil.listar();
            
        } catch (Exception ex) {
            
            jsfUtil.addMensagemErro("Ocorreu um erro ao calcular a conta");
        }
        
    }
    
    public void preparaCalcularPorcentagemServicoEntradaCouvert() {
      
        if (configuracao.getEntrada_couvert().intValue() <= 0 && configuracao.getPorcentagemServico() <= 0) {
            
            jsfUtil.addMensagemInfo("NÃo há¡ valores definidos para cobrança de Entrada/Couvert e (%) Serviço");
            
            calcularConta();
            
        } else {
            
            jsfUtil.alterar();
            
        }
    }
    
    public void calcularEntradaCouvert() {
        
        try{
            
        entrada_couvert = entrada_couvert.add(configuracao.getEntrada_couvert().multiply(BigDecimal.valueOf(quantidadeEntradaCouvert)));
        
        }catch(Exception ex){
             
            jsfUtil.addMensagemErro("Ocorreu um erro ao calcular entrada ou couvert");
        }
    }
    
    public void calcularPorcentagemServico() {
        
        try{
            
        porcentagemDoServico = porcentagemDoServico.add(subTotal.multiply(BigDecimal.valueOf(configuracao.getPorcentagemServico())).divide(new BigDecimal("100")));
        
        }catch(Exception ex){
            
            jsfUtil.addMensagemErro("Ocorreu  um erro  ao calcular porcentagem do serviÃ§o");
        }
        }
    
    public void gerarCupomDaConta() throws JRException {
       
        final List<Pedido> listaPedidosRelatorio = new ArrayList<>();
        
        for (Pedido p : listaPedidosPagar) {
            
            Pedido ped = new Pedido();
            
            if (configuracao.getTipoCobranca().equalsIgnoreCase(TipoCobranca.CARTÃO.toString())) {
                
                ped.setMesa_cartao(String.valueOf(p.getCartao().getNumeroCartao()));
                
            } else {
                
                ped.setMesa_cartao(p.getMesa().getNumeroMesa());
                
            }
            
            ped.setSub_total(subTotal);
            ped.setPorc_servico(porcentagemDoServico);
            ped.setEntrada_couvert(entrada_couvert);
            ped.setTotal(totalConta);
            ped.setNomeItem(p.getItemCardapio().getNome());
            ped.setValor(p.getItemCardapio().getValor());
            ped.setQtde(p.getQuantidade().getQuantString());
            ped.setQtde_vezes_valor(p.getItemCardapio().getValor().multiply(p.getQuantidade().getQuantidade()));
            
            listaPedidosRelatorio.add(ped);
        }
        
        final HashMap map = new HashMap();
        
        final String caminhoRelatorio = FacesContext.getCurrentInstance().getExternalContext().getRealPath("relatorios/pedidos.jasper");
        final String caminhoPdf = FacesContext.getCurrentInstance().getExternalContext().getRealPath("relatorios/");
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaPedidosRelatorio, false);
        jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, map, beanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, caminhoPdf + "/pedido.pdf");
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.addHeader("Content-disposition", "inline);filename=pedido.pdf");
        ServletOutputStream servletOutputStream = null;
        
        try {
            
            servletOutputStream = httpServletResponse.getOutputStream();
            
        } catch (IOException ex) {
            
            jsfUtil.addMensagemErro("Ocorreu um erro ao gerar o cupom! ");
        }
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public Configuracao getConfiguracao() {
        return configuracao;
    }
    
    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public List<Pedido> getListaPedidosPagar() {
        return listaPedidosPagar;
    }
    
    public BigDecimal getPorcentagemDoServico() {
        return porcentagemDoServico;
    }
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
    
    public BigDecimal getTotalConta() {
        return totalConta;
    }
    
    public boolean isCobrarPorcentagemDoServico() {
        return cobrarPorcentagemDoServico;
    }
    
    public void setCobrarPorcentagemDoServico(boolean cobrarPorcentagemDoServico) {
        this.cobrarPorcentagemDoServico = cobrarPorcentagemDoServico;
    }
    
    public boolean isCobrarEntradaCouvert() {
        return cobrarEntradaCouvert;
    }
    
    public void setCobrarEntradaCouvert(boolean cobrarEntradaCouvert) {
        this.cobrarEntradaCouvert = cobrarEntradaCouvert;
    }
    
    public int getQuantidadeEntradaCouvert() {
        return quantidadeEntradaCouvert;
    }
    
    public void setQuantidadeEntradaCouvert(int quantidadeEntradaCouvert) {
        this.quantidadeEntradaCouvert = quantidadeEntradaCouvert;
    }
    
    public void setPorcentagemDoServico(BigDecimal porcentagemDoServico) {
        this.porcentagemDoServico = porcentagemDoServico;
    }
    
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    public void setTotalConta(BigDecimal totalConta) {
        this.totalConta = totalConta;
    }
    
    public BigDecimal getEntrada_couvert() {
        return entrada_couvert;
    }
    
    public void setEntrada_couvert(BigDecimal entrada_couvert) {
        this.entrada_couvert = entrada_couvert;
    }
}
