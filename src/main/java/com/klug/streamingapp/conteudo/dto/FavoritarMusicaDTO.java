package com.klug.streamingapp.conteudo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FavoritarMusicaDTO {
    private UUID usuarioId;
    private UUID musicaId;
}
