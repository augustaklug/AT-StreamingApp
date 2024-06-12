package com.klug.streamingapp.usuarios.service;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Transacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntifraudeService {

    public boolean validarTransacao(Cartao cartao, Transacao transacao) {
        if (!cartao.getAtivo()) {
            throw new IllegalArgumentException("Cartão não está ativo");
        }

        List<Transacao> ultimasTransacoes = cartao.getTransacoes().stream()
                .filter(t -> t.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(cartao.getTransacaoIntervaloTempo())))
                .collect(Collectors.toList());

        if (ultimasTransacoes.size() >= cartao.getTransacaoQuantidadeAltaFrequencia()) {
            return false;
        }

        List<Transacao> transacoesRepetidas = ultimasTransacoes.stream()
                .filter(t -> t.getComerciante().equals(transacao.getComerciante()) && t.getValor() == transacao.getValor())
                .collect(Collectors.toList());

        return transacoesRepetidas.size() < cartao.getTransacaoMerchantDuplicadas();
    }
}