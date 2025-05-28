package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Avaliacao;
import com.umc.pfc_fastprice.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    // Método para criar o registro de uma avaliação
    @PostMapping("/avaliacao/cadastrar")
    @ResponseBody
    public ResponseEntity<Void> registrarAvaliacao(@RequestBody Avaliacao avaliacao) {
        avaliacaoService.registrarAvaliacao(avaliacao);
        return ResponseEntity.ok().build();
    }
    
    // Método ADMIN para atualizar o registro de uma avaliação
    @PostMapping("/admin/avaliacao/atualizar")
    public String adminAtualizarAvaliacao(@ModelAttribute Avaliacao avaliacao, RedirectAttributes atributos) {
        avaliacaoService.atualizarAvaliacao(avaliacao);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
    
    // Método ADMIN para deletar o registro de uma avaliação
    @PostMapping("/admin/avaliacao/deletar/{id}")
    public String adminDeletarAvaliacao(@PathVariable String id, RedirectAttributes atributos) {
        avaliacaoService.deletarAvaliacao(id);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}