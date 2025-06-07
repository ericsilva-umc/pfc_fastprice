package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.AutenticarUsuarioService;
import com.umc.pfc_fastprice.service.UsuarioService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AutenticarUsuarioService autenticarUsuarioService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private Usuario usuarioExistente;

    @BeforeEach
    void setUp() {
        usuarioExistente = new Usuario();
        usuarioExistente.setId("1");
        usuarioExistente.setEmail("ja@existe.com");
    }

    @Test
    void testCadastrarUsuario_EmailJaEmUso() {
        when(usuarioService.buscarEmail("ja@existe.com")).thenReturn(usuarioExistente);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("ja@existe.com");

        String view = controller.cadastrarUsuario(novoUsuario, model);

        assertEquals("cadastrar", view);
        verify(model).addAttribute(eq("erro"), anyString());
    }

    @Test
    void testCadastrarUsuario_EmailDisponivel() {
        when(usuarioService.buscarEmail("livre@email.com")).thenReturn(null);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("livre@email.com");

        String view = controller.cadastrarUsuario(novoUsuario, model);

        assertEquals("redirect:/login", view);
        verify(usuarioService).criarUsuario(novoUsuario);
    }

    @Test
    void testAtualizarUsuario_EmailJaEmUsoPorOutro() {
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId("2");
        outroUsuario.setEmail("ja@existe.com");

        Usuario usuarioAtual = new Usuario();
        usuarioAtual.setId("1");
        usuarioAtual.setEmail("ja@existe.com");

        when(usuarioService.buscarEmail("ja@existe.com")).thenReturn(outroUsuario);

        String view = controller.atualizarUsuario(usuarioAtual, model);

        assertEquals("configuracoes", view);
        verify(model).addAttribute("erro", "O e-mail já está em uso!");
        verify(model).addAttribute("modoEdicao", true);
    }

    @Test
    void testAtualizarUsuario_EmailValido() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("novo@email.com");

        when(usuarioService.buscarEmail("novo@email.com")).thenReturn(null);

        UserDetails userDetails = mock(UserDetails.class);
        when(autenticarUsuarioService.loadUserByUsername("novo@email.com")).thenReturn(userDetails);

        String view = controller.atualizarUsuario(usuario, model);

        assertEquals("redirect:/configurar", view);
        verify(usuarioService).atualizarUsuario(usuario);
        verify(autenticarUsuarioService).loadUserByUsername("novo@email.com");
    }

    @Test
    void testAdminAtualizarUsuario_EmailEmUsoPorOutro() {
        Usuario usuario = new Usuario();
        usuario.setId("2");
        usuario.setEmail("ja@existe.com");

        when(usuarioService.buscarEmail(any())).thenReturn(usuarioExistente);

        mockUsuarioLogado("ja@existe.com", "1");

        String view = controller.adminAtualizarUsuario(usuario, redirectAttributes);

        assertEquals("redirect:/admin", view);
        verify(redirectAttributes).addFlashAttribute("mostrarAlerta", true);
        verify(redirectAttributes).addFlashAttribute(eq("mensagemAlerta"), contains("não realizado"));
    }

    @Test
    void testAdminAtualizarUsuario_AtualizacaoDoProprioUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("novo@proprio.com");

        when(usuarioService.buscarEmail(any())).thenReturn(usuario);

        mockUsuarioLogado("antigo@proprio.com", "1");

        UserDetails userDetails = mock(UserDetails.class);
        when(autenticarUsuarioService.loadUserByUsername("novo@proprio.com")).thenReturn(userDetails);

        String view = controller.adminAtualizarUsuario(usuario, redirectAttributes);

        assertEquals("redirect:/admin", view);
        verify(autenticarUsuarioService).loadUserByUsername("novo@proprio.com");
        verify(usuarioService).atualizarUsuario(usuario);
    }

    @Test
    void testAdminAtualizarUsuario_DeOutroUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId("2");
        usuario.setEmail("usuario2@email.com");

        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId("1");
        usuarioLogado.setEmail("logado@email.com");

        when(usuarioService.buscarEmail(any())).thenReturn(usuarioLogado).thenReturn(null);

        mockUsuarioLogado("logado@email.com", "1");

        String view = controller.adminAtualizarUsuario(usuario, redirectAttributes);

        assertEquals("redirect:/admin", view);
        verify(usuarioService).atualizarUsuario(usuario);
        verify(redirectAttributes).addFlashAttribute("mostrarAlerta", true);
        verify(redirectAttributes).addFlashAttribute(eq("mensagemAlerta"), contains("realizado"));
    }

    @Test
    void testAdminDeletarUsuario_TentativaDeAutoExclusao() {
        Usuario usuario = new Usuario();
        usuario.setId("1");

        when(usuarioService.buscarEmail(any())).thenReturn(usuario);
        when(usuarioService.buscarUsuario("1")).thenReturn(usuario);

        mockUsuarioLogado("email@logado.com", "1");

        String view = controller.adminDeletarUsuario("1", redirectAttributes);

        assertEquals("redirect:/admin", view);
        verify(redirectAttributes).addFlashAttribute(eq("mensagemAlerta"), contains("não pode excluir"));
    }

    @Test
    void testAdminDeletarUsuario_OutroUsuario() {
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId("1");

        Usuario outroUsuario = new Usuario();
        outroUsuario.setId("2");

        when(usuarioService.buscarEmail(any())).thenReturn(usuarioLogado);
        when(usuarioService.buscarUsuario("2")).thenReturn(outroUsuario);

        mockUsuarioLogado("logado@email.com", "1");

        String view = controller.adminDeletarUsuario("2", redirectAttributes);

        assertEquals("redirect:/admin", view);
        verify(usuarioService).deletarUsuario("2");
        verify(redirectAttributes).addFlashAttribute(eq("mensagemAlerta"), contains("realizado"));
    }

    private void mockUsuarioLogado(String email, String id) {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(id);
        mockUsuario.setEmail(email);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }
}