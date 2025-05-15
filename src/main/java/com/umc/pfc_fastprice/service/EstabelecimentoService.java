package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.repository.EstabelecimentoRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    // Método da service que utiliza o repository para o cadastro de um estabelecimento
    public Estabelecimento cadastrarEstabelecimento(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento); // Salva o estabelecimento no banco de dados
    }

    // Método da service que utiliza o repository para atualizar o registro de um estabelecimento
    public Estabelecimento atualizarEstabelecimento(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento); // Atualiza o estabelecimento no banco de dados
    }
    
    // Método da service que utiliza o repository para retornar a lista de estabelecimentos
    public List<Estabelecimento> listarEstabelecimentos() {
        return estabelecimentoRepository.findAll();
    }

    // Método da service que utiliza o repository para retornar um estabelecimento por id
    public Estabelecimento buscarEstabelecimentoPorId(String id) {
        return estabelecimentoRepository.findById(id).orElse(null);
    }
    
    // Método da service que utiliza o repository para deletar o registro de um usuário
    public void deletarEstabelecimento(String id) {
	estabelecimentoRepository.deleteById(id);
    }
}
