package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    // Método ADMIN para criar o registro de um estabelecimento
    @PostMapping("/admin/estabelecimento/cadastrar")
    public String adminCadastrarEstabelecimento(@ModelAttribute Estabelecimento estabelecimento, RedirectAttributes atributos) {
        estabelecimentoService.cadastrarEstabelecimento(estabelecimento);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }

    // Método ADMIN para atualizar o registro de um estabelecimento
    @PostMapping("/admin/estabelecimento/atualizar")
    public String adminAtualizarEstabelecimento(@ModelAttribute Estabelecimento estabelecimento, RedirectAttributes atributos) {
        estabelecimentoService.atualizarEstabelecimento(estabelecimento);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }

    // Método ADMIN para deletar o registro de um estabelecimento
    @PostMapping("/admin/estabelecimento/deletar/{id}")
    public String adminDeletarEstabelecimento(@PathVariable String id, RedirectAttributes atributos) {
        estabelecimentoService.deletarEstabelecimento(id);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}
