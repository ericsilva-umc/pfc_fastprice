package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.repository.ListaDeCompraRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ListaDeCompraServiceTest {

    @Mock
    private ListaDeCompraRepository listaRepository;

    @InjectMocks
    private ListaDeCompraService listaService;

    private ListaDeCompra lista;

    @BeforeEach
    public void setUp() {
        lista = new ListaDeCompra();
        lista.setId("1");
        lista.setUsuarioId("usuario123");
        lista.setItens(new ArrayList<>(List.of("item1", "item2")));
    }

    @Test
    public void testDeveBuscarListaPorIdUsuario() {
        when(listaRepository.findByUsuarioId("usuario123")).thenReturn(Optional.of(lista));

        ListaDeCompra resultado = listaService.buscarListaPorIdUsuario("usuario123");

        assertNotNull(resultado);
        assertEquals(lista, resultado);
        verify(listaRepository).findByUsuarioId("usuario123");
    }

    @Test
    public void testDeveRetornarNullQuandoListaNaoExiste() {
        when(listaRepository.findByUsuarioId("usuario123")).thenReturn(Optional.empty());

        ListaDeCompra resultado = listaService.buscarListaPorIdUsuario("usuario123");

        assertNull(resultado);
        verify(listaRepository).findByUsuarioId("usuario123");
    }

    @Test
    public void testDeveCadastrarLista() {
        when(listaRepository.save(lista)).thenReturn(lista);

        ListaDeCompra resultado = listaService.cadastrarLista(lista);

        assertEquals(lista, resultado);
        verify(listaRepository).save(lista);
    }

    @Test
    public void testDeveAtualizarLista() {
        when(listaRepository.save(lista)).thenReturn(lista);

        ListaDeCompra resultado = listaService.atualizarLista(lista);

        assertEquals(lista, resultado);
        verify(listaRepository).save(lista);
    }

    @Test
    public void testDeveCriarNovaLista() {
        ListaDeCompra novaLista = listaService.criarNovaLista("usuarioABC");

        assertNotNull(novaLista);
        assertEquals("usuarioABC", novaLista.getUsuarioId());
        assertNotNull(novaLista.getItens());
        assertTrue(novaLista.getItens().isEmpty());
    }

    @Test
    public void testDeveRetornarTrueSeItemExisteNaLista() {
        boolean resultado = listaService.itemExisteNaLista(lista, "item1");

        assertTrue(resultado);
    }

    @Test
    public void testDeveRetornarFalseSeItemNaoExisteNaLista() {
        boolean resultado = listaService.itemExisteNaLista(lista, "itemX");

        assertFalse(resultado);
    }

    @Test
    public void testDeveAdicionarItemNaLista() {
        ListaDeCompra listaMockada = spy(lista);

        doReturn(listaMockada).when(listaRepository).save(any());

        listaService.adicionarItemNaLista(listaMockada, "item3");

        assertTrue(listaMockada.getItens().contains("item3"));
        verify(listaRepository).save(listaMockada);
    }
}