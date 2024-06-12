package com.klug.streamingapp.usuarios.service;

import com.klug.streamingapp.conteudo.service.PlaylistService;
import com.klug.streamingapp.usuarios.model.*;

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

    @Autowired
    private PlaylistService playlistService;

    public UsuarioDTO criarConta(UsuarioDTO usuarioDTO, Cartao cartao, Plano plano) throws Exception {
        CPF cpf = new CPF(usuarioDTO.getCpf());
        Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), passwordEncoder.encode(usuarioDTO.getSenha()), cpf);

        usuario.getCartoes().add(cartao);
        usuario.setAssinatura(new Assinatura(plano));

        usuarioRepository.save(usuario);

        playlistService.criarPlaylistDefault(usuario);

        return new UsuarioDTO(usuario);
    }
}