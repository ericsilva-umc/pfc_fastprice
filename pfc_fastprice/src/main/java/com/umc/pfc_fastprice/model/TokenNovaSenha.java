package com.umc.pfc_fastprice.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokennovasenha")
public class TokenNovaSenha {
    
    @Id
    String id;
    String token;
    String usuarioId;
    LocalDateTime dataExpiracao;
    
    public TokenNovaSenha() {}
    
    public TokenNovaSenha(String token, String usuarioId, LocalDateTime dataExpiracao) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.dataExpiracao = dataExpiracao;
    }    

    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataexpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
    //</editor-fold>
}
