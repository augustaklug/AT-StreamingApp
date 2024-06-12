package com.klug.streamingapp.usuarios.service;


import com.klug.streamingapp.usuarios.model.Assinatura;
import com.klug.streamingapp.usuarios.model.Plano;
import com.klug.streamingapp.usuarios.model.Usuario;
import com.klug.streamingapp.usuarios.dto.AssinaturaDTO;
import com.klug.streamingapp.usuarios.repository.PlanoRepository;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssinaturaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    public AssinaturaDTO criarAssinatura(AssinaturaDTO assinaturaDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(assinaturaDTO.getUsuarioId())
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        if (usuario.getAssinatura() != null && usuario.getAssinatura().isAtivo()) {
            throw new Exception("Usuário já possui uma assinatura ativa.");
        }

        Plano plano = planoRepository.findById(assinaturaDTO.getPlanoId())
                .orElseThrow(() -> new Exception("Plano não encontrado"));

        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setDtAssinatura(LocalDateTime.now());
        assinatura.setAtivo(true);
        usuario.setAssinatura(assinatura);
        usuarioRepository.save(usuario);

        return new AssinaturaDTO(assinatura);
    }
}
