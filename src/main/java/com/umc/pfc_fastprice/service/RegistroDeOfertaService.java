package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.repository.RegistroDeOfertaRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegistroDeOfertaService {

    @Autowired
    RegistroDeOfertaRepository ofertasRepository;

    // Método da service que utiliza o repository para retornar a lista de ofertas
    public RegistroDeOferta buscarOfertaPorId(String id) {
        return ofertasRepository.findById(id).orElse(null);
    }
    
    // Método da service que utiliza o repository para retornar a lista de ofertas
    public List<RegistroDeOferta> listarOfertas() {
        return ofertasRepository.findAll();
    }

    // Método da service que utiliza o repository para retornar a lista de ofertas em formato paginável
    public Page<RegistroDeOferta> listarOfertasEmPaginas(Pageable pageable) {
        return ofertasRepository.findAll(pageable);
    }

    // Método da service que utiliza o repository para o cadastro de oferta
    public RegistroDeOferta cadastrarOferta(RegistroDeOferta oferta) {
        return ofertasRepository.save(oferta); // Salva a oferta no banco de dados
    }
    
    // Método da service que utiliza o repository para atualizar o registro de oferta
    public RegistroDeOferta atualizarOferta(RegistroDeOferta oferta) {
        return ofertasRepository.save(oferta); // Atualiza a oferta no banco de dados
    }
    
    // Método da service que utiliza o repository para deletar o registro de oferta
    public void deletarOferta(String id) {
	ofertasRepository.deleteById(id);
    }
    
    // Método da service para buscar as ofertas que possuem um produto que corresponde ao termo de busca
    public Page<RegistroDeOferta> buscarOfertasPorProduto(String termo, Pageable pageable) {
        return ofertasRepository.findByProdutoContainingIgnoreCase(termo, pageable);
    }
    
    // Método da service para buscar produtos que correspondem ao termo de busca em uma lista de estabelecimentos
    public Page<RegistroDeOferta> buscarOfertasPorProdutoEmEstabelecimentos(String termo, List<String> estabelecimentoIDs, Pageable pageable) {
        return ofertasRepository.findByProdutoContainingIgnoreCaseAndEstabelecimentoIdIn(termo, estabelecimentoIDs, pageable);
    }
    
    // Método da service que utiliza o repository para retornar ofertas de uma lista de estabelecimentos
    public Page<RegistroDeOferta> buscarOfertasEmEstabelecimentos(List<String> estabelecimentoIds, Pageable pageable) {
        return ofertasRepository.findByEstabelecimentoIdIn(estabelecimentoIds, pageable);
    }
    
    // Método da service que utiliza o repository para retornar a lista de ofertas vinculadas ao ID de um estabelecimento em específico
    public List<RegistroDeOferta> buscarOfertasPorIdEstabelecimento(String id) {
        return ofertasRepository.findByEstabelecimentoId(id);
    }
}