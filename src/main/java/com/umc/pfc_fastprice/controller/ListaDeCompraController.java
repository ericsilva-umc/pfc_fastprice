package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.ListaDeCompra;
import com.umc.pfc_fastprice.repository.ListaDeCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listas")
public class ListaDeCompraController {

    @Autowired
    private ListaDeCompraRepository listaRepository;

    @PostMapping
    public ListaDeCompra criarLista(@RequestBody ListaDeCompra listaDeCompra) {
        return listaRepository.insert(listaDeCompra);
    }

    @GetMapping
    public List<ListaDeCompra> listarListas() {
        return listaRepository.findAll();
    }

    @PutMapping("/{id}")
    public String atualizarLista(@PathVariable String id, @RequestBody ListaDeCompra listaDeCompra) {
        Optional<ListaDeCompra> listaBusca = listaRepository.findById(id);

        if (listaBusca.isPresent()) {
            listaDeCompra.setId(id);
            listaRepository.save(listaDeCompra);

            return "Lista atualizada com sucesso.";
        }

        return "Lista não existe.";
    }

    @GetMapping("/{id}")
    public ListaDeCompra buscaLista(@PathVariable String id) {
        return listaRepository.findById(id).orElseThrow(() -> new RuntimeException("Lista não encontrada. ID: " + id));
    }

    @DeleteMapping("/{id}")
    public String deletarLista(@PathVariable String id) {
        if (listaRepository.existsById(id)) {
            listaRepository.deleteById(id);
            return "Lista removida com sucesso.";
        } else {
            return "Lista não encontrada.";
        }
    }
}
