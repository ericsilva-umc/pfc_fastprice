package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.Estabelecimento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoRepository extends MongoRepository<Estabelecimento, String> {
}
