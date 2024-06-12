package com.klug.streamingapp.usuarios.dto;


import com.klug.streamingapp.usuarios.domain.Transacao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransacaoDTO {
    private UUID id;
    private UUID cartaoId;
    private double valor;
    private String merchant;
    private String descricao;
    private LocalDateTime dtTransacao;

    public TransacaoDTO() {}

    public TransacaoDTO(Transacao transacao) {
        this.id = transacao.getCodigoAutorizacao();
        this.cartaoId = transacao.getCartao().getId();
        this.valor = transacao.getValor();
        this.merchant = transacao.getComerciante();
        this.descricao = transacao.getDescricao();
        this.dtTransacao = transacao.getDtTransacao();
    }
}
