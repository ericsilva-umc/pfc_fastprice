package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeCompraRepository extends MongoRepository<ListaDeCompra, String> {
    @Query("{ 'usuarioId' : ?0 }")
    Optional<ListaDeCompra> findByUsuarioId(String usuarioId);
}
