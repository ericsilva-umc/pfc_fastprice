package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import com.umc.pfc_fastprice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PutMapping("/{id}")
    public String atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioBusca = usuarioRepository.findById(id);

        if (usuarioBusca.isPresent()) {
            Usuario usuarioEncontrado = usuarioBusca.get();

            usuario.setId(usuarioEncontrado.getId());
            usuario.setSenha(usuarioEncontrado.getSenha());

            usuarioRepository.save(usuario);

            return "Usuário atualizado com sucesso.";
        }

        return "Usuário não existe.";
    }

    @GetMapping("/verificar-senha")
    public Boolean verificarSenha(@RequestParam String email, @RequestParam String senha) {
        Optional<Usuario> usuarioBusca = usuarioRepository.findByEmail(email);

        if (usuarioBusca.isPresent()) {
            Usuario usuarioEncontrado = usuarioBusca.get();
            //return BCrypt.checkpw(senha, usuarioEncontrado.getSenha());
        }

        return false;
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado. ID: " + id));
    }

    @DeleteMapping("/{id}")
    public String deletarUsuario(@PathVariable String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return "Usuário removido com sucesso.";
        } else {
            return "Usuário não encontrado.";
        }
    }
}
