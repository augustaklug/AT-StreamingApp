package com.klug.streamingapp.conteudo.service;

import com.klug.streamingapp.conteudo.model.Musica;
import com.klug.streamingapp.conteudo.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    public Musica obterMusica(UUID musicaId) throws Exception {
        return musicaRepository.findById(musicaId).orElseThrow(() -> new Exception("Música não encontrada"));
    }
}
