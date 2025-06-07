package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.repository.EstabelecimentoRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EstabelecimentoServiceTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    private Estabelecimento estabelecimento;

    @BeforeEach
    public void setUp() {
        estabelecimento = new Estabelecimento();
        estabelecimento.setId("1");
        estabelecimento.setNome("Teste");
    }

    @Test
    public void testDeveCadastrarEstabelecimento() {
        when(estabelecimentoRepository.save(estabelecimento)).thenReturn(estabelecimento);

        Estabelecimento resultado = estabelecimentoService.cadastrarEstabelecimento(estabelecimento);

        assertEquals(estabelecimento, resultado);
        verify(estabelecimentoRepository).save(estabelecimento);
    }

    @Test
    public void testDeveAtualizarEstabelecimento() {
        when(estabelecimentoRepository.save(estabelecimento)).thenReturn(estabelecimento);

        Estabelecimento resultado = estabelecimentoService.atualizarEstabelecimento(estabelecimento);

        assertEquals(estabelecimento, resultado);
        verify(estabelecimentoRepository).save(estabelecimento);
    }

    @Test
    public void testDeveListarEstabelecimentos() {
        List<Estabelecimento> lista = Arrays.asList(estabelecimento);
        when(estabelecimentoRepository.findAll()).thenReturn(lista);

        List<Estabelecimento> resultado = estabelecimentoService.listarEstabelecimentos();

        assertEquals(1, resultado.size());
        assertEquals(estabelecimento, resultado.get(0));
        verify(estabelecimentoRepository).findAll();
    }

    @Test
    public void testDeveBuscarEstabelecimentoPorId() {
        when(estabelecimentoRepository.findById("1")).thenReturn(Optional.of(estabelecimento));

        Estabelecimento resultado = estabelecimentoService.buscarEstabelecimentoPorId("1");

        assertNotNull(resultado);
        assertEquals(estabelecimento, resultado);
        verify(estabelecimentoRepository).findById("1");
    }

    @Test
    public void testDeveRetornarNullQuandoEstabelecimentoNaoForEncontradoPorId() {
        when(estabelecimentoRepository.findById("999")).thenReturn(Optional.empty());

        Estabelecimento resultado = estabelecimentoService.buscarEstabelecimentoPorId("999");

        assertNull(resultado);
        verify(estabelecimentoRepository).findById("999");
    }

    @Test
    public void testDeveBuscarEstabelecimentosPorNome() {
        List<Estabelecimento> lista = Arrays.asList(estabelecimento);
        when(estabelecimentoRepository.findByNomeContainingIgnoreCase("teste")).thenReturn(lista);

        List<Estabelecimento> resultado = estabelecimentoService.buscarEstabelecimentosPorNome("teste");

        assertEquals(1, resultado.size());
        assertEquals(estabelecimento, resultado.get(0));
        verify(estabelecimentoRepository).findByNomeContainingIgnoreCase("teste");
    }

    @Test
    public void testDeveDeletarEstabelecimento() {
        String id = "1";

        estabelecimentoService.deletarEstabelecimento(id);

        verify(estabelecimentoRepository).deleteById(id);
    }
}