package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.repository.SugestaoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SugestaoService {

    @Autowired
    SugestaoRepository sugestaoRepository;

    // Método da service que utiliza o repository para o cadastro de uma sugestão
    public Sugestao registrarSugestao(Sugestao sugestao) {
        return sugestaoRepository.save(sugestao); // Salva a sugestão no banco de dados
    }

    // Método da service que utiliza o repository para retornar a lista de sugestões
    public List<Sugestao> listarSugestoes() {
        return sugestaoRepository.findAll();
    }

    // Método da service que utiliza o repository para atualizar o registro de uma sugestão
    public Sugestao atualizarSugestao(Sugestao sugestao) {
        return sugestaoRepository.save(sugestao); // Atualiza a sugestão no banco de dados
    }
    
    // Método da service que utiliza o repository para deletar o registro de uma sugestão
    public void deletarSugestao(String id) {
	sugestaoRepository.deleteById(id);
    }
}
