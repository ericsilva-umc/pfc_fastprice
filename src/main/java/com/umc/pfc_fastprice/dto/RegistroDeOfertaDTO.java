package com.umc.pfc_fastprice.dto;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Usuario;

public class RegistroDeOfertaDTO {
    private RegistroDeOferta registro;
    private Usuario usuario;
    private Estabelecimento estabelecimento;

    public RegistroDeOfertaDTO(RegistroDeOferta registro, Usuario usuario, Estabelecimento estabelecimento) {
        this.registro = registro;
        this.usuario = usuario;
        this.estabelecimento = estabelecimento;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public RegistroDeOferta getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroDeOferta registro) {
        this.registro = registro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
    //</editor-fold>
}
