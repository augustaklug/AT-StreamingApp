package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.dto.PlaylistDTO;
import com.klug.streamingapp.conteudo.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistDTO> criarPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        try {
            PlaylistDTO novaPlaylist = playlistService.criarPlaylist(playlistDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPlaylist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
