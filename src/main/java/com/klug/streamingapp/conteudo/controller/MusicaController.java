package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @PostMapping("/favoritar")
    public ResponseEntity<Void> favoritarMusica(@RequestBody FavoritarMusicaDTO favoritarMusicaDTO) {
        try {
            musicaService.favoritarMusica(favoritarMusicaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
