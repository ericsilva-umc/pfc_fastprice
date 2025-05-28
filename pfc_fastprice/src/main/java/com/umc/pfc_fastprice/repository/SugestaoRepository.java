package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.Sugestao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SugestaoRepository extends MongoRepository<Sugestao, String> {
}
