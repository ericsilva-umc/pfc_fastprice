package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.Avaliacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends MongoRepository<Avaliacao, String> {
}
