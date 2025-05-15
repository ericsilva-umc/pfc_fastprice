package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Estabelecimento;
import com.umc.pfc_fastprice.model.RegistroDeOferta;
import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.EstabelecimentoService;
import com.umc.pfc_fastprice.service.RegistroDeOfertaService;
import com.umc.pfc_fastprice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroDeOfertaController {

    @Autowired
    RegistroDeOfertaService registroDeOfertaService;

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @Autowired
    UsuarioService usuarioService;

    // Método para redirecionar à página para cadastro de oferta
    @PostMapping("/registrodeoferta/cadastrar")
    public String cadastrarOferta(@ModelAttribute RegistroDeOferta oferta, @RequestParam String estabelecimentoId) {
        Estabelecimento estabelecimento = estabelecimentoService.buscarEstabelecimentoPorId(estabelecimentoId); // Busca o ID do estabelecimento no banco de dados
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // Obtém o e-mail do usuário logado através da sessão
        Usuario usuarioBusca = usuarioService.buscarEmail(email); // Busca por um usuário no banco de dados utilizando o e-mail

        oferta.setUsuarioId(usuarioBusca.getId()); // Define no registro da oferta o ID do usuário logado
        oferta.setEstabelecimentoId(estabelecimento.getId()); // Define no registro de oferta o ID do estabelecimento encontrado
        oferta.setPositivo("0"); // Define com zero o atributo "positivo"
        oferta.setNegativo("0"); // Define com zero o atributo "negativo"

        registroDeOfertaService.cadastrarOferta(oferta); // Cadastra o objeto de oferta montado no banco de dados
        return "redirect:/"; // Redireciona à página principal
    }

    // Método ADMIN para atualizar o registro de uma oferta
    @PostMapping("/admin/registrodeoferta/atualizar")
    public String adminAtualizarOferta(@ModelAttribute RegistroDeOferta oferta, RedirectAttributes atributos) {
        registroDeOfertaService.atualizarOferta(oferta);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
    
    // Método ADMIN para deletar o registro de uma oferta
    @PostMapping("/admin/registrodeoferta/deletar/{id}")
    public String adminDeletarOferta(@PathVariable String id, RedirectAttributes atributos) {
        registroDeOfertaService.deletarOferta(id);

        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}