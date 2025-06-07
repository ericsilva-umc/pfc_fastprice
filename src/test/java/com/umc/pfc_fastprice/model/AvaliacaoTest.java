package com.umc.pfc_fastprice.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AvaliacaoTest {

    @Test
    void testGetSetId() {
        Avaliacao avaliacao = new Avaliacao();
        String id = "123";
        avaliacao.setId(id);
        assertEquals(id, avaliacao.getId());
    }

    @Test
    void testGetSetEstabelecimentoId() {
        Avaliacao avaliacao = new Avaliacao();
        String estId = "123";
        avaliacao.setEstabelecimentoId(estId);
        assertEquals(estId, avaliacao.getEstabelecimentoId());
    }

    @Test
    void testGetSetPreco() {
        Avaliacao avaliacao = new Avaliacao();
        String preco = "5";
        avaliacao.setPreco(preco);
        assertEquals(preco, avaliacao.getPreco());
    }

    @Test
    void testGetSetAtendimento() {
        Avaliacao avaliacao = new Avaliacao();
        String atendimento = "4";
        avaliacao.setAtendimento(atendimento);
        assertEquals(atendimento, avaliacao.getAtendimento());
    }

    @Test
    void testGetSetTempo() {
        Avaliacao avaliacao = new Avaliacao();
        String tempo = "3";
        avaliacao.setTempo(tempo);
        assertEquals(tempo, avaliacao.getTempo());
    }

    @Test
    void testGetSetVariedade() {
        Avaliacao avaliacao = new Avaliacao();
        String variedade = "5";
        avaliacao.setVariedade(variedade);
        assertEquals(variedade, avaliacao.getVariedade());
    }
}