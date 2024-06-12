package com.klug.streamingapp.conteudo.dto;

import com.klug.streamingapp.conteudo.domain.Playlist;
import lombok.Data;

import java.util.UUID;

@Data
public class PlaylistDTO {
    private UUID id;
    private UUID usuarioId;
    private String nome;

    public PlaylistDTO() {}

    public PlaylistDTO(Playlist playlist) {
        this.id = playlist.getId();
        this.nome = playlist.getNome();
    }
}
