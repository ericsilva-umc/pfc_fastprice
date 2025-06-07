package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegistroDeOfertaControllerTest {

    @InjectMocks
    private RegistroDeOfertaController controller;

    @Mock
    private RegistroDeOfertaService registroDeOfertaService;

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId("user123");
        mockUsuario.setEmail("teste@exemplo.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(mockUsuario.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        when(usuarioService.buscarEmail(mockUsuario.getEmail())).thenReturn(mockUsuario);
    }

    @Test
    void testCadastrarOferta() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId("est123");

        when(estabelecimentoService.buscarEstabelecimentoPorId("est123")).thenReturn(estabelecimento);

        String result = controller.cadastrarOferta(oferta, "est123");

        assertEquals("redirect:/", result);
        verify(registroDeOfertaService).cadastrarOferta(oferta);
        assertEquals("est123", oferta.getEstabelecimentoId());
        assertEquals("user123", oferta.getUsuarioId());
    }

    @Test
    void testNovoVotoPositivo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setPositivo(new ArrayList<>());
        oferta.setNegativo(new ArrayList<>());

        when(registroDeOfertaService.buscarOfertaPorId("oferta1")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "positivo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta1", avaliacao);

        assertTrue(oferta.getPositivo().contains("user123"));
        assertFalse(oferta.getNegativo().contains("user123"));
        verify(registroDeOfertaService).atualizarOferta(oferta);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRetirarVotoPositivo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setPositivo(new ArrayList<>(List.of("user123")));
        oferta.setNegativo(new ArrayList<>());

        when(registroDeOfertaService.buscarOfertaPorId("oferta2")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "positivo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta2", avaliacao);

        assertFalse(oferta.getPositivo().contains("user123"));
        verify(registroDeOfertaService).atualizarOferta(oferta);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testTrocarNegativoParaPositivo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setPositivo(new ArrayList<>());
        oferta.setNegativo(new ArrayList<>(List.of("user123")));

        when(registroDeOfertaService.buscarOfertaPorId("oferta3")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "positivo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta3", avaliacao);

        assertTrue(oferta.getPositivo().contains("user123"));
        assertFalse(oferta.getNegativo().contains("user123"));
    }

    @Test
    void testNovoVotoNegativo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setPositivo(new ArrayList<>());
        oferta.setNegativo(new ArrayList<>());

        when(registroDeOfertaService.buscarOfertaPorId("oferta4")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "negativo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta4", avaliacao);

        assertTrue(oferta.getNegativo().contains("user123"));
        assertFalse(oferta.getPositivo().contains("user123"));
    }

    @Test
    void testRetirarVotoNegativo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setNegativo(new ArrayList<>(List.of("user123")));
        oferta.setPositivo(new ArrayList<>());

        when(registroDeOfertaService.buscarOfertaPorId("oferta5")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "negativo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta5", avaliacao);

        assertFalse(oferta.getNegativo().contains("user123"));
    }

    @Test
    void testTrocarPositivoParaNegativo() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        oferta.setPositivo(new ArrayList<>(List.of("user123")));
        oferta.setNegativo(new ArrayList<>());

        when(registroDeOfertaService.buscarOfertaPorId("oferta6")).thenReturn(oferta);

        Map<String, String> avaliacao = Map.of("tipo", "negativo");
        ResponseEntity<String> response = controller.avaliarOferta("oferta6", avaliacao);

        assertTrue(oferta.getNegativo().contains("user123"));
        assertFalse(oferta.getPositivo().contains("user123"));
    }

    @Test
    void testAdminAtualizarOferta() {
        RegistroDeOferta oferta = new RegistroDeOferta();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String result = controller.adminAtualizarOferta(oferta, redirectAttributes);

        assertEquals("redirect:/admin", result);
        verify(registroDeOfertaService).atualizarOferta(oferta);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("mostrarAlerta"));
    }

    @Test
    void testAdminDeletarOferta() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String result = controller.adminDeletarOferta("oferta123", redirectAttributes);

        assertEquals("redirect:/admin", result);
        verify(registroDeOfertaService).deletarOferta("oferta123");
    }
}