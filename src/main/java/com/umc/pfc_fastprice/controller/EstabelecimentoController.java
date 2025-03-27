package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @PostMapping
    public Estabelecimento criarEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoRepository.insert(estabelecimento);
    }

    @GetMapping
    public List<Estabelecimento> listarEstabelecimentos() {
        return estabelecimentoRepository.findAll();
    }

    @PutMapping("/{id}")
    public String atualizarEstabelecimento(@PathVariable String id, @RequestBody Estabelecimento estabelecimento) {
        Optional<Estabelecimento> estabelecimentoBusca = estabelecimentoRepository.findById(id);

        if (estabelecimentoBusca.isPresent()) {
            Estabelecimento estabelecimentoEncontrado = estabelecimentoBusca.get();

            estabelecimento.setId(estabelecimentoEncontrado.getId());

            estabelecimentoRepository.save(estabelecimento);

            return "Estabelecimento atualizado com sucesso.";
        }

        return "Estabelecimento não existe.";
    }

    @GetMapping("/{id}")
    public Estabelecimento buscaEstabelecimento(@PathVariable String id) {
        return estabelecimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado. ID: " + id));
    }

    @DeleteMapping("/{id}")
    public String deletarEstabelecimento(@PathVariable String id) {
        if (estabelecimentoRepository.existsById(id)) {
            estabelecimentoRepository.deleteById(id);
            return "Estabelecimento removido com sucesso.";
        } else {
            return "Estabelecimento não encontrado.";
        }
    }
}
