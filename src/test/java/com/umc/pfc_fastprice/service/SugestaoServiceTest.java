package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.repository.SugestaoRepository;
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
public class SugestaoServiceTest {

    @Mock
    private SugestaoRepository sugestaoRepository;

    @InjectMocks
    private SugestaoService sugestaoService;

    private Sugestao sugestao;

    @BeforeEach
    void setUp() {
        sugestao = new Sugestao();
        sugestao.setId("1");
    }

    @Test
    void testDeveRegistrarSugestao() {
        when(sugestaoRepository.save(sugestao)).thenReturn(sugestao);

        Sugestao resultado = sugestaoService.registrarSugestao(sugestao);

        assertEquals(sugestao, resultado);
        verify(sugestaoRepository).save(sugestao);
    }

    @Test
    void testDeveListarSugestoes() {
        List<Sugestao> lista = Arrays.asList(sugestao);
        when(sugestaoRepository.findAll()).thenReturn(lista);

        List<Sugestao> resultado = sugestaoService.listarSugestoes();

        assertEquals(1, resultado.size());
        verify(sugestaoRepository).findAll();
    }

    @Test
    void testDeveAtualizarSugestao() {
        when(sugestaoRepository.save(sugestao)).thenReturn(sugestao);

        Sugestao resultado = sugestaoService.atualizarSugestao(sugestao);

        assertEquals(sugestao, resultado);
        verify(sugestaoRepository).save(sugestao);
    }

    @Test
    void testDeveDeletarSugestao() {
        sugestaoService.deletarSugestao("1");

        verify(sugestaoRepository).deleteById("1");
    }
}