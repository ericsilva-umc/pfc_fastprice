package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
        return usuarioRepository.insert(usuario);
    }
    
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    
    @PutMapping("/{id}")
    public String atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioBusca = usuarioRepository.findById(id);
        
        if(usuarioBusca.isPresent()) {
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
        
        if(usuarioBusca.isPresent()) {
            Usuario usuarioEncontrado = usuarioBusca.get();
            return BCrypt.checkpw(senha, usuarioEncontrado.getSenha());
        }
        
        return false;
    }

    @GetMapping("/{id}")
    public Usuario buscaUsuario(@PathVariable String id) {
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
