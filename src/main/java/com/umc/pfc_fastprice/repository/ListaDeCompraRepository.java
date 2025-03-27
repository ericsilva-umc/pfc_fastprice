package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeCompraRepository extends MongoRepository<ListaDeCompra, String> {
}
