package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroDeOfertaRepository extends MongoRepository<RegistroDeOferta, String> {
}
