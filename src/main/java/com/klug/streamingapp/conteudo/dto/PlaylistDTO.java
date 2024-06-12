package com.klug.streamingapp.conteudo.dto;

import com.klug.streamingapp.conteudo.model.Playlist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlaylistDTO {
    private UUID id;
    private String nome;
    private UUID usuarioId;

    public PlaylistDTO(Playlist playlist) {
        this.id = playlist.getId();
        this.nome = playlist.getNome();
        this.usuarioId = playlist.getUsuario().getId();
    }
}
