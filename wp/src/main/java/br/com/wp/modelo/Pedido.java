package br.com.wp.modelo;

import br.com.wp.enumeracao.StatusPedido;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Wilson F Florindo
 */

@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dataPedido;

    @NotNull(message = "Funcionário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "funcionario")
    private Funcionario funcionario;

    @NotNull(message = "Item do cardapio é obrigatório")
    @ManyToOne
    @JoinColumn(name = "cardapio")
    private Cardapio itemCardapio;

    @NotNull(message = "Quantidade é obrigatório")
    @ManyToOne
    @JoinColumn(name = "quantidade")
    private Quantidade quantidade;

    @NotNull(message = "Número da mesa é obrigatório")
    @ManyToOne
    @JoinColumn(name = "mesa")
    private Mesa mesa;

    @NotNull(message = "Número do cartão é obrigatório")
    @JoinColumn(name = "cartao")
    @ManyToOne
    private Cartao cartao;
    
    @Column(name = "status_pedido", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

     @Transient
    private BigDecimal valor;

    @Transient
    private String numCartao;
    
    @Transient
    private String mesa_cartao;

    @Transient
    private BigDecimal sub_total;

    @Transient
    private BigDecimal qtde_vezes_valor;
    
    @Transient
    private BigDecimal porc_servico;
    
     @Transient
    private BigDecimal entrada_couvert;
     
       @Transient
    private BigDecimal total;

    @Transient
    private String nomeItem;

    @Transient
    private String qtde;
    
    
    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wp.modelo.Pedido[ id=" + id + " ]";
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(Cardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Quantidade quantidade) {
        this.quantidade = quantidade;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSub_total() {
        return sub_total;
    }

    public void setSub_total(BigDecimal sub_total) {
        this.sub_total = sub_total;
    }

    public BigDecimal getQtde_vezes_valor() {
        return qtde_vezes_valor;
    }

    public void setQtde_vezes_valor(BigDecimal qtde_vezes_valor) {
        this.qtde_vezes_valor = qtde_vezes_valor;
    }

    

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getQtde() {
        return qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = qtde;
    }

    public BigDecimal getPorc_servico() {
        return porc_servico;
    }

    public void setPorc_servico(BigDecimal porc_servico) {
        this.porc_servico = porc_servico;
    }

    public BigDecimal getEntrada_couvert() {
        return entrada_couvert;
    }

    public void setEntrada_couvert(BigDecimal entrada_couvert) {
        this.entrada_couvert = entrada_couvert;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }
    

    public Cartao getCartao() {
        return cartao;
    }

  
    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getMesa_cartao() {
        return mesa_cartao;
    }

    public void setMesa_cartao(String mesa_cartao) {
        this.mesa_cartao = mesa_cartao;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
    

}
