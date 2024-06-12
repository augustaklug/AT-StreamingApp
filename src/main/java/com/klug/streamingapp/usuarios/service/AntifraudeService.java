package com.klug.streamingapp.usuarios.service;


import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Transacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntifraudeService {

    public boolean validarTransacao(Cartao cartao, Transacao transacao) {
        // Verificar se o cartão está ativo
        if (!cartao.getAtivo()) {
            throw new IllegalArgumentException("Cartão não está ativo");
        }

        // Verificar transações nos últimas 2 minutos
        List<Transacao> ultimasTransacoes = cartao.getTransacoes().stream()
                .filter(t -> t.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
                .collect(Collectors.toList());

        // Verificar alta frequência de transações
        if (ultimasTransacoes.size() >= 3) {
            return false;
        }

        // Verificar transações duplicadas
        List<Transacao> transacoesRepetidas = ultimasTransacoes.stream()
                .filter(t -> t.getComerciante().equals(transacao.getComerciante()) && Double.compare(t.getValor(), transacao.getValor()) == 0)
                .collect(Collectors.toList());

        return transacoesRepetidas.size() < 2;
    }
}