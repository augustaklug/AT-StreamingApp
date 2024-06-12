package com.klug.streamingapp.usuarios.service;

import com.klug.streamingapp.conteudo.service.PlaylistService;
import com.klug.streamingapp.usuarios.model.*;

import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.repository.CartaoRepository;
import com.klug.streamingapp.usuarios.repository.PlanoRepository;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlaylistService playlistService;

    public UsuarioDTO criarConta(UsuarioDTO usuarioDTO, UUID cartaoId, UUID planoId) throws Exception {
        CPF cpf = new CPF(usuarioDTO.getCpf());
        Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), passwordEncoder.encode(usuarioDTO.getSenha()), cpf);

        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new Exception("Cartão não encontrado"));
        Plano plano = planoRepository.findById(planoId).orElseThrow(() -> new Exception("Plano não encontrado"));

        // Validar o cartão usando o método do próprio modelo
        cartao.validarCartao();

        usuario.getCartoes().add(cartao);
        usuario.setAssinatura(new Assinatura(plano));

        usuarioRepository.save(usuario);

        playlistService.criarPlaylistDefault(usuario);

        return new UsuarioDTO(usuario);
    }
}