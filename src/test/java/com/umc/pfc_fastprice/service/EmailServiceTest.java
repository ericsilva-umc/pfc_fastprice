package com.umc.pfc_fastprice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testDeveEnviarEmailDeRedefinicaoDeSenha() {
        String destinatario = "usuario@teste.com";
        String link = "http://localhost:8080/redefinir-senha?token=abc123";

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.enviarEmailRedefinicaoSenha(destinatario, link);

        verify(javaMailSender).send(captor.capture());

        SimpleMailMessage mensagemEnviada = captor.getValue();

        assertEquals("email@gmail.com", mensagemEnviada.getFrom());
        assertEquals(destinatario, mensagemEnviada.getTo()[0]);
        assertEquals("Redefinição de senha", mensagemEnviada.getSubject());
        assertTrue(mensagemEnviada.getText().contains(link));
        assertTrue(mensagemEnviada.getText().contains("validade de 30 minutos"));
    }
}