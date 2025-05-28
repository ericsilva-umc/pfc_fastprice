package com.umc.pfc_fastprice.controller.rest;

import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.repository.RegistroDeOfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registros")
public class RegistroDeOfertaRESTController {

    @Autowired
    private RegistroDeOfertaRepository registroRepository;

    @PostMapping
    public RegistroDeOferta criarRegistro(@RequestBody RegistroDeOferta registroDeOferta) {
        return registroRepository.insert(registroDeOferta);
    }

    @GetMapping
    public List<RegistroDeOferta> listarRegistros() {
        return registroRepository.findAll();
    }

    @PutMapping("/{id}")
    public String atualizarRegistro(@PathVariable String id, @RequestBody RegistroDeOferta registroDeOferta) {
        Optional<RegistroDeOferta> registroBusca = registroRepository.findById(id);

        if (registroBusca.isPresent()) {
            registroDeOferta.setId(id);
            registroRepository.save(registroDeOferta);

            return "Registro atualizado com sucesso.";
        }

        return "Registro não existe.";
    }

    @GetMapping("/{id}")
    public RegistroDeOferta buscaRegistro(@PathVariable String id) {
        return registroRepository.findById(id).orElseThrow(() -> new RuntimeException("Registro não encontrado. ID: " + id));
    }

    @DeleteMapping("/{id}")
    public String deletarRegistro(@PathVariable String id) {
        if (registroRepository.existsById(id)) {
            registroRepository.deleteById(id);
            return "Registro removido com sucesso.";
        } else {
            return "Registro não encontrado.";
        }
    }
}
