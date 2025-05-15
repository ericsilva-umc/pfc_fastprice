package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.Endereco;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String> {
    @Query("{ 'usuarioId' : ?0 }")
    Optional<Endereco> findByUsuarioId(String usuarioId);
}