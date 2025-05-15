package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Endereco;
import com.umc.pfc_fastprice.repository.EnderecoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    // Método da service que utiliza o repository para o cadastro de endereço
    public Endereco cadastrarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco); // Salva o endereço no banco de dados
    }
    
    // Método da service que utilizar o repository para a atualização de um endereço
    public Endereco atualizarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Método da service que utiliza o repository para buscar o endereço de um usuário
    public Optional<Endereco> buscarEnderecoPorIdUsuario(String id) {
        return enderecoRepository.findByUsuarioId(id); // Retorna o endereço encontrado no banco de dados
    }
}
