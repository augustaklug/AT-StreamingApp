package com.klug.streamingapp.usuarios.service;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Plano;
import com.klug.streamingapp.usuarios.domain.Usuario;
import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO criarConta(UsuarioDTO usuarioDTO, Cartao cartao, Plano plano) throws Exception {
        Usuario usuario = new Usuario();
        String senhaCodificada = passwordEncoder.encode(usuarioDTO.getSenha());

        usuario.criar(usuarioDTO.getNome(), usuarioDTO.getEmail(), senhaCodificada, usuarioDTO.getCpf(), cartao, plano);
        usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario);
    }
}