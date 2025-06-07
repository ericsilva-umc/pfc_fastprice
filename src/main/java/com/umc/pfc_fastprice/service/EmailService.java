package com.umc.pfc_fastprice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void enviarEmailRedefinicaoSenha(String email, String linkRedefinicao) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom("email@gmail.com");
        message.setTo(email);
        message.setSubject("Redefinição de senha");
        message.setText("Clique no link abaixo para definir uma nova senha:\n\n"
                        + linkRedefinicao + "\n\n"
                        + "OBS.: O link tem validade de 30 minutos.\n\n"
                        + "Se você não solicitou isso, por gentileza ignore este e-mail.\n\n"
                        + "Atenciosamente, \nEquipe FastPrice");

        javaMailSender.send(message);
    }
}
