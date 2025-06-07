package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Endereco;
import com.umc.pfc_fastprice.repository.EnderecoRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco endereco;

    @BeforeEach
    public void setUp() {
        endereco = new Endereco();
        endereco.setId("1");
        endereco.setRua("Rua teste");
        endereco.setCidade("São Paulo");
    }

    @Test
    public void testDeveCadastrarEndereco() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco resultado = enderecoService.cadastrarEndereco(endereco);

        assertEquals(endereco, resultado);
        verify(enderecoRepository).save(endereco);
    }

    @Test
    public void testDeveAtualizarEndereco() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco resultado = enderecoService.atualizarEndereco(endereco);

        assertEquals(endereco, resultado);
        verify(enderecoRepository).save(endereco);
    }

    @Test
    public void testDeveBuscarEnderecoPorIdDoUsuario() {
        String idUsuario = "usuario123";
        when(enderecoRepository.findByUsuarioId(idUsuario)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoService.buscarEnderecoPorIdUsuario(idUsuario);

        assertTrue(resultado.isPresent());
        assertEquals(endereco, resultado.get());
        verify(enderecoRepository).findByUsuarioId(idUsuario);
    }
}