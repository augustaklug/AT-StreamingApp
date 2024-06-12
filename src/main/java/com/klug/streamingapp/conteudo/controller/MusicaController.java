package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.model.Musica;
import com.klug.streamingapp.conteudo.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/{musicaId}")
    public ResponseEntity<Musica> obterMusica(@PathVariable UUID musicaId) {
        try {
            Musica musica = musicaService.obterMusica(musicaId);
            return ResponseEntity.ok(musica);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}