package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.TokenNovaSenha;
import com.umc.pfc_fastprice.repository.TokenNovaSenhaRepository;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenNovaSenhaService {

    @Autowired
    TokenNovaSenhaRepository tokenRepository;

    public TokenNovaSenha validarToken(String token) {
        return tokenRepository.findByToken(token).filter(t -> t.getDataExpiracao().isAfter(LocalDateTime.now())).orElse(null);
    }
    
    // Método da service para excluir qualquer token associado ao usuário
    public void deletarTokenAssociado(String id) {
        tokenRepository.deleteByUsuarioId(id);
    }
    
    // Método da service para salvar o token para redefinição de senha no banco de dados
    public TokenNovaSenha salvarToken(TokenNovaSenha token) {
        return tokenRepository.save(token);
    }
}
