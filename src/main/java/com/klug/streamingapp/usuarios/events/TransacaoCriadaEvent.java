package com.klug.streamingapp.usuarios.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TransacaoCriadaEvent {
    private final UUID transacaoId;
    private final double valor;
    private final LocalDateTime dtTransacao;

    public TransacaoCriadaEvent(UUID transacaoId, double valor, LocalDateTime dtTransacao) {
        this.transacaoId = transacaoId;
        this.valor = valor;
        this.dtTransacao = dtTransacao;
    }

}
