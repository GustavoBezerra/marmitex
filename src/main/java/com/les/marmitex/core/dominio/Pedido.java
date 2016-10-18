package com.les.marmitex.core.dominio;

import java.util.List;

/**
 * Classe para representar o pedido
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class Pedido extends EntidadeDominio{

    private List<Marmitex> marmitex;
    private double valorFrete;
    private double valorTotal;
    private String status;
    private Endereco endereco;
    private Entregador entregador;
    private List<Pagamento> pagamento;
    private double troco;
    private Cliente cliente;

    /**
     * @return the marmitex
     */
    public List<Marmitex> getMarmitex() {
        return marmitex;
    }

    /**
     * @param marmitex the marmitex to set
     */
    public void setMarmitex(List<Marmitex> marmitex) {
        this.marmitex = marmitex;
    }

    /**
     * @return the valorFrete
     */
    public double getValorFrete() {
        return valorFrete;
    }

    /**
     * @param valorFrete the valorFrete to set
     */
    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    /**
     * @return the valorTotal
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the entregador
     */
    public Entregador getEntregador() {
        return entregador;
    }

    /**
     * @param entregador the entregador to set
     */
    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    /**
     * @return the pagamento
     */
    public List<Pagamento> getPagamento() {
        return pagamento;
    }

    /**
     * @param pagamento the pagamento to set
     */
    public void setPagamento(List<Pagamento> pagamento) {
        this.pagamento = pagamento;
    }

    /**
     * @return the troco
     */
    public double getTroco() {
        return troco;
    }

    /**
     * @param troco the troco to set
     */
    public void setTroco(double troco) {
        this.troco = troco;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}
