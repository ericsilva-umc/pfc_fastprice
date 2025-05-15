package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Sugestao;
import com.umc.pfc_fastprice.service.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SugestaoController {

    @Autowired
    SugestaoService sugestaoService;

    // Método para criar o registro de uma sugestão
    @PostMapping("/sugestao/cadastrar")
    public String registrarSugestao(@ModelAttribute Sugestao sugestao, RedirectAttributes atributos) {
        sugestaoService.registrarSugestao(sugestao);
        
        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Sugestão enviada com sucesso!");
        return "redirect:/sugestoes";
    }
    
    // Método ADMIN para atualizar o registro de uma sugestão
    @PostMapping("/admin/sugestao/atualizar")
    public String adminAtualizarSugestao(@ModelAttribute Sugestao sugestao, RedirectAttributes atributos) {
        sugestaoService.atualizarSugestao(sugestao);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
    
    // Método ADMIN para deletar o registro de uma sugestão
    @PostMapping("/admin/sugestao/deletar/{id}")
    public String adminDeletarSugestao(@PathVariable String id, RedirectAttributes atributos) {
        sugestaoService.deletarSugestao(id);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}