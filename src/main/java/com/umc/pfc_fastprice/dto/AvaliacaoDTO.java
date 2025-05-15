package com.umc.pfc_fastprice.dto;

import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.model.Estabelecimento;

public class AvaliacaoDTO {
    private Avaliacao avaliacao;
    private Estabelecimento estabelecimento;

    public AvaliacaoDTO(Avaliacao avaliacao, Estabelecimento estabelecimento) {
        this.avaliacao = avaliacao;
        this.estabelecimento = estabelecimento;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Get/Set">
    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
    //</editor-fold>
}
