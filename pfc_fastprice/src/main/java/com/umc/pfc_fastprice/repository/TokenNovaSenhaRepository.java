package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.TokenNovaSenha;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenNovaSenhaRepository extends MongoRepository<TokenNovaSenha, String> {
    Optional<TokenNovaSenha> findByToken(String token);
    void deleteByUsuarioId(String id);
}