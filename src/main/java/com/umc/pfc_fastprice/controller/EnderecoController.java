package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Endereco;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.EnderecoService;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    UsuarioService usuarioService;

    // Método para criar o registro de um endereço
    @PostMapping("/endereco/cadastrar")
    public String cadastrarEndereco(@ModelAttribute Endereco endereco) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);

        endereco.setUsuarioId(usuarioBusca.getId());

        enderecoService.cadastrarEndereco(endereco);
        return "redirect:/endereco";
    }

    // Método para atualizar o registro de um endereço
    @PostMapping("/endereco/atualizar")
    @ResponseBody
    public ResponseEntity<Void> atualizarEndereco(@RequestBody Endereco endereco) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioBusca = usuarioService.buscarEmail(email);
        Optional<Endereco> enderecoBusca = enderecoService.buscarEnderecoPorIdUsuario(usuarioBusca.getId());

        endereco.setId(enderecoBusca.get().getId());
        endereco.setUsuarioId(enderecoBusca.get().getUsuarioId());
        endereco.setComplemento(enderecoBusca.get().getComplemento());

        enderecoService.atualizarEndereco(endereco);
        return ResponseEntity.ok().build();
    }

    // Método ADMIN para atualizar o registro de um endereço
    @PostMapping("/admin/endereco/atualizar")
    public String adminAtualizarEndereco(@ModelAttribute Endereco endereco, RedirectAttributes atributos) {
        enderecoService.atualizarEndereco(endereco);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}
