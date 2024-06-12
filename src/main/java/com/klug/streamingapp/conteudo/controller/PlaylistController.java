package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.dto.PlaylistDTO;
import com.klug.streamingapp.conteudo.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistDTO> criarPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        try {
            PlaylistDTO novaPlaylist = playlistService.criarPlaylist(playlistDTO);
            return ResponseEntity.ok(novaPlaylist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<Void> adicionarMusica(@PathVariable UUID playlistId, @PathVariable UUID musicaId) {
        try {
            playlistService.adicionarMusica(playlistId, musicaId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<Void> removerMusica(@PathVariable UUID playlistId, @PathVariable UUID musicaId) {
        try {
            playlistService.removerMusica(playlistId, musicaId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/favoritar")
    public ResponseEntity<Void> favoritarMusica(@RequestBody FavoritarMusicaDTO favoritarMusicaDTO) {
        try {
            playlistService.favoritarMusica(favoritarMusicaDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}