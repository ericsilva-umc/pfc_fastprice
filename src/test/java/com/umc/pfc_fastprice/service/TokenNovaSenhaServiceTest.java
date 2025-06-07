package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.TokenNovaSenha;
import com.umc.pfc_fastprice.repository.TokenNovaSenhaRepository;
import java.time.LocalDateTime;
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
public class TokenNovaSenhaServiceTest {

    @Mock
    private TokenNovaSenhaRepository tokenRepository;

    @InjectMocks
    private TokenNovaSenhaService tokenService;

    private TokenNovaSenha tokenValido;
    private TokenNovaSenha tokenExpirado;

    @BeforeEach
    void setUp() {
        tokenValido = new TokenNovaSenha();
        tokenValido.setToken("tokenValido");
        tokenValido.setDataexpiracao(LocalDateTime.now().plusHours(1));

        tokenExpirado = new TokenNovaSenha();
        tokenExpirado.setToken("tokenExpirado");
        tokenExpirado.setDataexpiracao(LocalDateTime.now().minusHours(1));
    }

    @Test
    void testDeveValidarTokenQuandoValido() {
        when(tokenRepository.findByToken("tokenValido")).thenReturn(Optional.of(tokenValido));

        TokenNovaSenha resultado = tokenService.validarToken("tokenValido");

        assertNotNull(resultado);
        assertEquals("tokenValido", resultado.getToken());
        verify(tokenRepository).findByToken("tokenValido");
    }

    @Test
    void testDeveRetornarNullQuandoTokenExpirado() {
        when(tokenRepository.findByToken("tokenExpirado")).thenReturn(Optional.of(tokenExpirado));

        TokenNovaSenha resultado = tokenService.validarToken("tokenExpirado");

        assertNull(resultado);
        verify(tokenRepository).findByToken("tokenExpirado");
    }

    @Test
    void testDeveRetornarNullQuandoTokenNaoEncontrado() {
        when(tokenRepository.findByToken("tokenInexistente")).thenReturn(Optional.empty());

        TokenNovaSenha resultado = tokenService.validarToken("tokenInexistente");

        assertNull(resultado);
        verify(tokenRepository).findByToken("tokenInexistente");
    }

    @Test
    void testDeveDeletarTokenAssociado() {
        tokenService.deletarTokenAssociado("usuario123");

        verify(tokenRepository).deleteByUsuarioId("usuario123");
    }

    @Test
    void testDeveSalvarToken() {
        when(tokenRepository.save(tokenValido)).thenReturn(tokenValido);

        TokenNovaSenha resultado = tokenService.salvarToken(tokenValido);

        assertEquals(tokenValido, resultado);
        verify(tokenRepository).save(tokenValido);
    }
}