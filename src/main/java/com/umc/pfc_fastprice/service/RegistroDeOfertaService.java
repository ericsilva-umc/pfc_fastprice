package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.repository.RegistroDeOfertaRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
}