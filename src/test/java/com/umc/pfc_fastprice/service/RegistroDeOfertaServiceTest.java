package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.repository.RegistroDeOfertaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class RegistroDeOfertaServiceTest {

    @Mock
    private RegistroDeOfertaRepository ofertasRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private RegistroDeOfertaService service;
    private RegistroDeOferta oferta;

    @BeforeEach
    public void setUp() {
        oferta = new RegistroDeOferta();
        oferta.setId("1");
        oferta.setProduto("Arroz");
        oferta.setEstabelecimentoId("est1");
    }

    @Test
    public void testDeveBuscarOfertaPorId() {
        when(ofertasRepository.findById("1")).thenReturn(Optional.of(oferta));

        RegistroDeOferta resultado = service.buscarOfertaPorId("1");

        assertEquals(oferta, resultado);
        verify(ofertasRepository).findById("1");
    }

    @Test
    public void testDeveRetornarNullQuandoOfertaNaoForEncontrada() {
        when(ofertasRepository.findById("2")).thenReturn(Optional.empty());

        RegistroDeOferta resultado = service.buscarOfertaPorId("2");

        assertNull(resultado);
        verify(ofertasRepository).findById("2");
    }

    @Test
    public void testDeveListarTodasAsOfertas() {
        List<RegistroDeOferta> lista = Arrays.asList(oferta);
        when(ofertasRepository.findAll()).thenReturn(lista);

        List<RegistroDeOferta> resultado = service.listarOfertas();

        assertEquals(1, resultado.size());
        verify(ofertasRepository).findAll();
    }

    @Test
    public void testDeveListarOfertasPaginadas() {
        Page<RegistroDeOferta> page = new PageImpl<>(List.of(oferta));
        when(ofertasRepository.findAll(pageable)).thenReturn(page);

        Page<RegistroDeOferta> resultado = service.listarOfertasEmPaginas(pageable);

        assertEquals(1, resultado.getTotalElements());
        verify(ofertasRepository).findAll(pageable);
    }

    @Test
    public void testDeveCadastrarOferta() {
        when(ofertasRepository.save(oferta)).thenReturn(oferta);

        RegistroDeOferta resultado = service.cadastrarOferta(oferta);

        assertEquals(oferta, resultado);
        verify(ofertasRepository).save(oferta);
    }

    @Test
    public void testDeveAtualizarOferta() {
        when(ofertasRepository.save(oferta)).thenReturn(oferta);

        RegistroDeOferta resultado = service.atualizarOferta(oferta);

        assertEquals(oferta, resultado);
        verify(ofertasRepository).save(oferta);
    }

    @Test
    public void testDeveDeletarOferta() {
        service.deletarOferta("1");

        verify(ofertasRepository).deleteById("1");
    }

    @Test
    public void testDeveBuscarOfertasPorProduto() {
        Page<RegistroDeOferta> page = new PageImpl<>(List.of(oferta));
        when(ofertasRepository.findByProdutoContainingIgnoreCase("arroz", pageable)).thenReturn(page);

        Page<RegistroDeOferta> resultado = service.buscarOfertasPorProduto("arroz", pageable);

        assertEquals(1, resultado.getContent().size());
        verify(ofertasRepository).findByProdutoContainingIgnoreCase("arroz", pageable);
    }

    @Test
    public void testDeveBuscarOfertasPorProdutoEmEstabelecimentos() {
        List<String> estabelecimentos = List.of("est1", "est2");
        Page<RegistroDeOferta> page = new PageImpl<>(List.of(oferta));
        when(ofertasRepository.findByProdutoContainingIgnoreCaseAndEstabelecimentoIdIn("arroz", estabelecimentos, pageable))
            .thenReturn(page);

        Page<RegistroDeOferta> resultado = service.buscarOfertasPorProdutoEmEstabelecimentos("arroz", estabelecimentos, pageable);

        assertEquals(1, resultado.getContent().size());
        verify(ofertasRepository).findByProdutoContainingIgnoreCaseAndEstabelecimentoIdIn("arroz", estabelecimentos, pageable);
    }

    @Test
    public void testDeveBuscarOfertasEmEstabelecimentos() {
        List<String> estabelecimentos = List.of("est1", "est2");
        Page<RegistroDeOferta> page = new PageImpl<>(List.of(oferta));
        when(ofertasRepository.findByEstabelecimentoIdIn(estabelecimentos, pageable)).thenReturn(page);

        Page<RegistroDeOferta> resultado = service.buscarOfertasEmEstabelecimentos(estabelecimentos, pageable);

        assertEquals(1, resultado.getContent().size());
        verify(ofertasRepository).findByEstabelecimentoIdIn(estabelecimentos, pageable);
    }

    @Test
    public void testDeveBuscarOfertasPorIdEstabelecimento() {
        List<RegistroDeOferta> lista = List.of(oferta);
        when(ofertasRepository.findByEstabelecimentoId("est1")).thenReturn(lista);

        List<RegistroDeOferta> resultado = service.buscarOfertasPorIdEstabelecimento("est1");

        assertEquals(1, resultado.size());
        verify(ofertasRepository).findByEstabelecimentoId("est1");
    }
}