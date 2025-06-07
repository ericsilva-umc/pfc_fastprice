package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.AvaliacaoService;
import com.umc.pfc_fastprice.service.EmailService;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.SugestaoService;
import com.umc.pfc_fastprice.service.TokenNovaSenhaService;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;


@ExtendWith(MockitoExtension.class)
class PaginasControllerTest {

    @InjectMocks
    private PaginasController controller;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private RegistroDeOfertaService ofertasService;

    @Mock
    private Model model;

    @Mock
    private Page<RegistroDeOferta> pageOfertas;

    @Mock
    private RegistroDeOferta ofertaMock;

    @Mock
    private Usuario usuarioMock;

    @Mock
    private Estabelecimento estabelecimentoMock;
    
    @Mock
    private AvaliacaoService avaliacaoService;

    @Mock
    private SugestaoService sugestaoService;

    @Mock
    private TokenNovaSenhaService tokenService;

    @Mock
    private EmailService emailService;
    
    @Value("${api.key.google.maps}")
    private String apiGoogleMaps = "CHAVE_TESTE";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "apiGoogleMaps", apiGoogleMaps);
    }

    @Test
    void testHomeSemFiltros() {
        // Mock do SecurityContext
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId("usuario123");
        usuarioLogado.setEmail("user@example.com");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        // Mock do usuário logado
        when(usuarioService.buscarEmail("user@example.com")).thenReturn(usuarioLogado);

        // Mock da página de ofertas sem filtros
        when(ofertasService.listarOfertasEmPaginas(any(Pageable.class))).thenReturn(pageOfertas);
        when(pageOfertas.getTotalPages()).thenReturn(1);

        // Mock da oferta retornada
        when(pageOfertas.iterator()).thenReturn(List.of(ofertaMock).iterator());
        when(ofertaMock.getUsuarioId()).thenReturn("usuario123");
        when(ofertaMock.getEstabelecimentoId()).thenReturn("estab123");

        when(usuarioService.buscarUsuario("usuario123")).thenReturn(usuarioMock);
        when(estabelecimentoService.buscarEstabelecimentoPorId("estab123")).thenReturn(estabelecimentoMock);

        // Execução
        String viewName = controller.home(null, null, 0, 10, model);

        // Verificações
        assertEquals("index", viewName);
        verify(model).addAttribute("usuarioId", "usuario123");
        verify(model).addAttribute("paginaAtual", 0);
        verify(model).addAttribute("totalPaginas", 1);
        verify(model).addAttribute(eq("ofertas"), any());
        verify(model).addAttribute("apiGoogleMaps", apiGoogleMaps);
    }
    
    @Test
    void testAdmin() {
        Usuario usuario1 = new Usuario(); usuario1.setId("u1"); usuario1.setNome("Usuário 1");
        Usuario usuario2 = new Usuario(); usuario2.setId("u2"); usuario2.setNome("Usuário 2");
        List<Usuario> usuarios = List.of(usuario1, usuario2);

        Estabelecimento est1 = new Estabelecimento(); est1.setId("e1"); est1.setNome("Estab 1");
        Estabelecimento est2 = new Estabelecimento(); est2.setId("e2"); est2.setNome("Estab 2");
        List<Estabelecimento> estabelecimentos = List.of(est1, est2);

        RegistroDeOferta oferta1 = mock(RegistroDeOferta.class);
        when(oferta1.getUsuarioId()).thenReturn("u1");
        when(oferta1.getEstabelecimentoId()).thenReturn("e1");
        RegistroDeOferta oferta2 = mock(RegistroDeOferta.class);
        when(oferta2.getUsuarioId()).thenReturn("u3"); // usuário não existe
        when(oferta2.getEstabelecimentoId()).thenReturn("e2");
        List<RegistroDeOferta> ofertas = List.of(oferta1, oferta2);

        Avaliacao avaliacao1 = mock(Avaliacao.class);
        when(avaliacao1.getEstabelecimentoId()).thenReturn("e1");
        Avaliacao avaliacao2 = mock(Avaliacao.class);
        when(avaliacao2.getEstabelecimentoId()).thenReturn("e3"); // estabelecimento não existe
        List<Avaliacao> avaliacoes = List.of(avaliacao1, avaliacao2);

        Sugestao sugestao1 = new Sugestao();
        Sugestao sugestao2 = new Sugestao();
        List<Sugestao> sugestoes = List.of(sugestao1, sugestao2);

        // Mocks de retorno dos serviços
        when(usuarioService.listarUsuarios()).thenReturn(usuarios);
        when(estabelecimentoService.listarEstabelecimentos()).thenReturn(estabelecimentos);
        when(ofertasService.listarOfertas()).thenReturn(ofertas);
        when(avaliacaoService.listarAvaliacoes()).thenReturn(avaliacoes);
        when(sugestaoService.listarSugestoes()).thenReturn(sugestoes);

        when(usuarioService.buscarUsuario("u1")).thenReturn(usuario1);
        when(usuarioService.buscarUsuario("u3")).thenReturn(null);

        when(estabelecimentoService.buscarEstabelecimentoPorId("e1")).thenReturn(est1);
        when(estabelecimentoService.buscarEstabelecimentoPorId("e2")).thenReturn(est2);
        when(estabelecimentoService.buscarEstabelecimentoPorId("e3")).thenReturn(null);

        // Executa o método
        String view = controller.admin(model);

        // Verifica retorno da view
        assertEquals("admin-painel", view);

        // Verifica os atributos adicionados ao Model
        verify(model).addAttribute("usuarios", usuarios);
        verify(model).addAttribute("estabelecimentos", estabelecimentos);
        verify(model).addAttribute(eq("ofertas"), anyList());
        verify(model).addAttribute(eq("avaliacoes"), anyList());
        verify(model).addAttribute("sugestoes", sugestoes);
        verify(model).addAttribute(eq("dashboard"), eq(List.of(usuarios.size(), estabelecimentos.size(), ofertas.size(), avaliacoes.size(), sugestoes.size())));
        verify(model).addAttribute("apiGoogleMaps", apiGoogleMaps);
    }
    
    @Test
    void testEmailRedefinicaoUsuarioEncontrado() {
        String email = "teste@example.com";
        Usuario usuario = new Usuario();
        usuario.setId("123");

        when(usuarioService.buscarEmail(email)).thenReturn(usuario);

        String redirect = controller.emailRedefinicao(email);

        assertEquals("redirect:/esqueceu-senha?emailenviado", redirect);

        verify(usuarioService).buscarEmail(email);
        verify(tokenService).deletarTokenAssociado(usuario.getId());
        verify(tokenService).salvarToken(argThat(tokenNovaSenha -> 
            tokenNovaSenha.getUsuarioId().equals(usuario.getId()) &&
            tokenNovaSenha.getDataExpiracao().isAfter(LocalDateTime.now())
        ));
        verify(emailService).enviarEmailRedefinicaoSenha(eq(email), contains("token="));
    }

    @Test
    void testEmailRedefinicaoUsuarioNaoEncontrado() {
        String email = "inexistente@example.com";

        when(usuarioService.buscarEmail(email)).thenReturn(null);

        String redirect = controller.emailRedefinicao(email);

        assertEquals("redirect:/esqueceu-senha?emailenviado", redirect);

        verify(usuarioService).buscarEmail(email);
        verifyNoInteractions(tokenService);
        verifyNoInteractions(emailService);
    }
}