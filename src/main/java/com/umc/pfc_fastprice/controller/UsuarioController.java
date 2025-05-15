package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.AutenticarUsuarioService;
import com.umc.pfc_fastprice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AutenticarUsuarioService autenticarUsuarioService;

    // Método para criar o registro de um novo usuário
    @PostMapping("/usuario/cadastrar")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.buscarEmail(usuario.getEmail()) != null) {
            model.addAttribute("erro", "O e-mail já está em uso!");
            return "cadastrar";
        }

        usuario.setAcesso("USER");
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }

    // Método para atualizar o registro do usuário editado
    @PostMapping("/usuario/atualizar")
    public String atualizarUsuario(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioBusca = usuarioService.buscarEmail(usuario.getEmail());

        if (usuarioBusca != null && !usuarioBusca.getId().equals(usuario.getId())) { // Confirma se o e-mail informado é diferente do anterior e se já está sendo usado
            model.addAttribute("erro", "O e-mail já está em uso!");
            model.addAttribute("modoEdicao", true);
            model.addAttribute("usuario", usuario);
            return "configuracoes";
        }

        usuarioService.atualizarUsuario(usuario); // Atualiza o registro do usuário após a edição

        // Atualiza o e-mail do usuário no contexto de sessão do Spring Security
        UserDetails userDetails = autenticarUsuarioService.loadUserByUsername(usuario.getEmail());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/configurar"; // Redireciona novamente à pagina de configurações do usuário
    }

    // Método ADMIN para atualizar o registro de um usuário
    @PostMapping("/admin/usuario/atualizar")
    public String adminAtualizarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes atributos) {
        Usuario usuarioLogado = usuarioService.buscarEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Usuario usuarioBusca = usuarioService.buscarEmail(usuario.getEmail());

        if (usuarioBusca != null && !usuarioBusca.getId().equals(usuario.getId())) { // Confirma se o e-mail informado é diferente do anterior e se já está sendo usado
            atributos.addFlashAttribute("mostrarAlerta", true);
            atributos.addFlashAttribute("mensagemAlerta", "Processo não realizado.\n\nO e-mail já está em uso.");
            return "redirect:/admin";
        }

        usuarioService.atualizarUsuario(usuario); // Atualiza o registro do usuário após a edição
        
        // Se o e-mail alterado for do usuário logado, atualiza a sessão do Spring Security
        if (usuario.getId().equals(usuarioLogado.getId())) {
            UserDetails userDetails = autenticarUsuarioService.loadUserByUsername(usuario.getEmail());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin"; // Redireciona ao painel de administrador
    }

    // Método ADMIN para deletar o registro de um usuário
    @PostMapping("/admin/usuario/deletar/{id}")
    public String adminDeletarUsuario(@PathVariable String id, RedirectAttributes atributos) {
        Usuario usuarioLogado = usuarioService.buscarEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Usuario usuarioBusca = usuarioService.buscarUsuario(id);

        if (usuarioLogado.getId().equals(usuarioBusca.getId())) {
            atributos.addFlashAttribute("mostrarAlerta", true);
            atributos.addFlashAttribute("mensagemAlerta", "Processo não realizado.\n\nVocê não pode excluir o seu próprio usuário.");
            return "redirect:/admin";
        }
        
        usuarioService.deletarUsuario(id); // Delete o registro do usuário de id específico
        
        atributos.addFlashAttribute("mostrarAlerta", true);
        atributos.addFlashAttribute("mensagemAlerta", "Processo realizado.");
        return "redirect:/admin";
    }
}
