package com.umc.pfc_fastprice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sugestao")
public class Sugestao {

    @Id
    private String id;
    private String usuarioId;
    private String tipo;
    private String localDoSite;
    private String descricao;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalDoSite() {
        return localDoSite;
    }

    public void setLocalDoSite(String localDoSite) {
        this.localDoSite = localDoSite;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    //</editor-fold>
}
