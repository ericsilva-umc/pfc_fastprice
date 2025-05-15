package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.repository.ListaDeCompraRepository;
import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaDeCompraService {

    @Autowired
    ListaDeCompraRepository listaRepository;

    // Método da service que utiliza o repository para buscar uma lista de compra pelo ID de usuário
    public ListaDeCompra buscarListaPorIdUsuario(String id) {
        return listaRepository.findByUsuarioId(id).orElse(null);
    }

    // Método da service que utiliza o repository para o cadastro de uma lista
    public ListaDeCompra cadastrarLista(ListaDeCompra lista) {
        return listaRepository.save(lista); // Salva a oferta no banco de dados
    }

    // Método da service que utiliza o repository para a atualização de uma lista
    public ListaDeCompra atualizarLista(ListaDeCompra lista) {
        return listaRepository.save(lista); // Salva a oferta no banco de dados
    }

    // Cria uma nova lista e retorna a referência
    public ListaDeCompra criarNovaLista(String usuarioId) {
        ListaDeCompra novaLista = new ListaDeCompra();
        List<String> novosItens = new ArrayList<>();
        novaLista.setUsuarioId(usuarioId);
        novaLista.setItens(novosItens);
        
        return novaLista;
    }
    
    // Verifica se o item existe na lista de compra e retorna true ou false
    public boolean itemExisteNaLista(ListaDeCompra lista, String id) {
        for (String idOferta : lista.getItens()) {
            if (idOferta.equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Adiciona um novo item na lista de compra
    public void adicionarItemNaLista(ListaDeCompra lista, String id) {
        List<String> itensNaLista = lista.getItens();
        itensNaLista.add(id);
        ListaDeCompra listaAtualizada = lista;
        lista.setItens(itensNaLista);
        atualizarLista(listaAtualizada);
    }
}
