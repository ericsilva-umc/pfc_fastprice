package com.umc.pfc_fastprice.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.annotation.CreatedDate;
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
    private List<String> positivo = new ArrayList<>();
    private List<String> negativo = new ArrayList<>();
    @CreatedDate
    private LocalDateTime dataRegistro;

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
    
    public List<String> getPositivo() {
        return positivo;
    }

    public void setPositivo(List<String> positivo) {
        this.positivo = positivo;
    }

    public List<String> getNegativo() {
        return negativo;
    }

    public void setNegativo(List<String> negativo) {
        this.negativo = negativo;
    }
        
    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    //</editor-fold>
}
