package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.repository.AvaliacaoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    // Método da service que utiliza o repository para o cadastro de uma avaliação
    public Avaliacao registrarAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao); // Salva a avaliação no banco de dados
    }

    // Método da service que utiliza o repository para retornar a lista de avaliações
    public List<Avaliacao> listarAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    // Método da service que utiliza o repository para atualizar o registro de uma avaliação
    public Avaliacao atualizarAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao); // Atualiza a avaliação no banco de dados
    }
    
    // Método da service que utiliza o repository para deletar o registro de uma avaliação
    public void deletarAvaliacao(String id) {
	avaliacaoRepository.deleteById(id);
    }
}
