package com.umc.pfc_fastprice.repository;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroDeOfertaRepository extends MongoRepository<RegistroDeOferta, String> {
    List<RegistroDeOferta> findByEstabelecimentoId(String id);
    Page<RegistroDeOferta> findByProdutoContainingIgnoreCase(String termo, Pageable pageable);
    Page<RegistroDeOferta> findByProdutoContainingIgnoreCaseAndEstabelecimentoIdIn(String termo, List<String> estabelecimentoIDs, Pageable pageable);
    Page<RegistroDeOferta> findByEstabelecimentoIdIn(List<String> estabelecimentoIDs, Pageable pageable);
    @Override
    Page<RegistroDeOferta> findAll(Pageable pageable);
}
