package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.repository.AvaliacaoRepository;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private Avaliacao avaliacao;

    @BeforeEach
    public void setUp() {
        avaliacao = new Avaliacao();
        avaliacao.setId("1");
    }

    @Test
    public void testDeveRegistrarAvaliacao() {
        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        Avaliacao resultado = avaliacaoService.registrarAvaliacao(avaliacao);

        assertEquals(avaliacao, resultado);
        verify(avaliacaoRepository).save(avaliacao);
    }

    @Test
    public void testDeveListarAvaliacoes() {
        List<Avaliacao> lista = Arrays.asList(avaliacao);
        when(avaliacaoRepository.findAll()).thenReturn(lista);

        List<Avaliacao> resultado = avaliacaoService.listarAvaliacoes();

        assertEquals(1, resultado.size());
        assertEquals(avaliacao, resultado.get(0));
        verify(avaliacaoRepository).findAll();
    }

    @Test
    public void testDeveAtualizarAvaliacao() {
        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        Avaliacao resultado = avaliacaoService.atualizarAvaliacao(avaliacao);

        assertEquals(avaliacao, resultado);
        verify(avaliacaoRepository).save(avaliacao);
    }

    @Test
    public void testDeveDeletarAvaliacao() {
        String id = "1";

        avaliacaoService.deletarAvaliacao(id);

        verify(avaliacaoRepository).deleteById(id);
    }
}