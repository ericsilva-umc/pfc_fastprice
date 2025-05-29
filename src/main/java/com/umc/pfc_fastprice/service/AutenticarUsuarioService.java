package com.umc.pfc_fastprice.service;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Substitui o método da classe UserDetails para configurar a forma de autenticação
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca um usuário através do e-mail informado
        Usuario usuarioBusca = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado: " + email));

	// Cria um novo objeto User com as informações do usuário encontrado
        return User
                .withUsername(usuarioBusca.getEmail()) // Usa o e-mail do usuário para montar o objeto
                .password(usuarioBusca.getSenha()) // Salva a senha
                .roles(usuarioBusca.getAcesso()) // Salva o tipo de acesso
                .build();
    }
}
