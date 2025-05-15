package com.umc.pfc_fastprice.model;

import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registrodeoferta")
public class RegistroDeOferta {

    @Id
    private String id;
    private String usuarioId;
    private String estabelecimentoId;
    private String produto;
    private double oferta;
    private String unidade;
    private String localizacao;
    private String positivo;
    private String negativo;

    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(String estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getOferta() {
        Locale ptBr = Locale.of("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(oferta);
    }

    public double getOfertaDouble() {
        return oferta;
    }

    public void setOferta(double oferta) {
        this.oferta = oferta;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getPositivo() {
        return positivo;
    }

    public void setPositivo(String positivo) {
        this.positivo = positivo;
    }

    public String getNegativo() {
        return negativo;
    }

    public void setNegativo(String negativo) {
        this.negativo = negativo;
    }
    //</editor-fold>
}
