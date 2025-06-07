package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@ExtendWith(MockitoExtension.class)
public class AutenticarUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticarUsuarioService autenticarUsuarioService;

    @Test
    public void testDeveRetornarUserDetailsQuandoUsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setSenha("123456");
        usuario.setAcesso("USER");

        when(usuarioRepository.findByEmail("teste@exemplo.com"))
            .thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticarUsuarioService.loadUserByUsername("teste@exemplo.com");

        assertEquals("teste@exemplo.com", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testDeveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findByEmail("naoexiste@exemplo.com"))
            .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            autenticarUsuarioService.loadUserByUsername("naoexiste@exemplo.com");
        });
    }
}