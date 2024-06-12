package com.klug.streamingapp.usuarios.dto;


import com.klug.streamingapp.usuarios.model.Assinatura;
import lombok.Data;

import java.util.UUID;

@Data
public class AssinaturaDTO {
    private UUID id;
    private UUID usuarioId;
    private UUID planoId;

    public AssinaturaDTO() {
    }

    public AssinaturaDTO(Assinatura assinatura) {
        this.id = assinatura.getId();
        this.planoId = assinatura.getPlano().getId();
    }
}
