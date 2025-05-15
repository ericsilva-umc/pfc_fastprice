package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    // Método da service para buscar o usuário no repository
    public Usuario buscarUsuario(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    // Método da service para buscar um usuário no repository pelo e-mail
    public Usuario buscarEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    
    // Método da service que utiliza o repository para o cadastro de usuário
    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Encriptografa a senha antes de salvar no banco de dados
        return usuarioRepository.save(usuario); // Salva o usuário no banco de dados
    }

    // Método da service que utiliza o repository para retornar a lista de usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método da service que utiliza o repository para atualizar o registro de um usuário
    public void atualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    // Método da service que utiliza o repository para deletar o registro de um usuário
    public void deletarUsuario(String id) {
	usuarioRepository.deleteById(id);
    }
}
