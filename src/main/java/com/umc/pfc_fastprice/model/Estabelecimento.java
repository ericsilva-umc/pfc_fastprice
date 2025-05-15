package com.umc.pfc_fastprice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estabelecimento")
public class Estabelecimento {

    @Id
    private String id;
    private String nome;

    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    //</editor-fold>
}
