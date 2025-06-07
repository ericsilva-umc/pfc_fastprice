package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("123");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senha123");
    }

    @Test
    void testDeveBuscarUsuarioPorIdQuandoExiste() {
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuario("123");

        assertNotNull(resultado);
        assertEquals("123", resultado.getId());
        verify(usuarioRepository).findById("123");
    }

    @Test
    void testDeveRetornarNullQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findById("999")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarUsuario("999");

        assertNull(resultado);
        verify(usuarioRepository).findById("999");
    }

    @Test
    void testDeveBuscarUsuarioPorEmailQuandoExiste() {
        when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarEmail("teste@teste.com");

        assertNotNull(resultado);
        assertEquals("teste@teste.com", resultado.getEmail());
        verify(usuarioRepository).findByEmail("teste@teste.com");
    }

    @Test
    void testDeveRetornarNullQuandoEmailNaoExiste() {
        when(usuarioRepository.findByEmail("naoexiste@teste.com")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.buscarEmail("naoexiste@teste.com");

        assertNull(resultado);
        verify(usuarioRepository).findByEmail("naoexiste@teste.com");
    }

    @Test
    void testDeveCriarUsuarioComSenhaEncriptada() {
        when(passwordEncoder.encode("senha123")).thenReturn("senhaEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.criarUsuario(usuario);

        assertNotNull(resultado);
        verify(passwordEncoder).encode("senha123");
        verify(usuarioRepository).save(usuario);
        assertEquals("senhaEncriptada", usuario.getSenha());
    }

    @Test
    void testDeveListarUsuarios() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testDeveAtualizarUsuario() {
        usuarioService.atualizarUsuario(usuario);

        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testDeveAtualizarSenhaComEncriptacao() {
        when(passwordEncoder.encode("novaSenha")).thenReturn("senhaNovaEncriptada");

        usuarioService.atualizarSenha(usuario, "novaSenha");

        verify(passwordEncoder).encode("novaSenha");
        verify(usuarioRepository).save(usuario);
        assertEquals("senhaNovaEncriptada", usuario.getSenha());
    }

    @Test
    void testDeveDeletarUsuario() {
        usuarioService.deletarUsuario("123");

        verify(usuarioRepository).deleteById("123");
    }
}