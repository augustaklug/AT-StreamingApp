package com.klug.streamingapp.usuarios.service;

import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AntifraudeServiceTest {

    private AntifraudeService antifraudeService;
    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        antifraudeService = new AntifraudeService();
        cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setAtivo(true);
        cartao.setTransacoes(new ArrayList<>());
    }

    @Test
    public void validarTransacao_CartaoNaoAtivo_DeveLancarExcecao() {
        cartao.setAtivo(false);

        Transacao transacao = criarTransacao();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            antifraudeService.validarTransacao(cartao, transacao);
        });

        assertEquals("Cartão não está ativo", exception.getMessage());
    }

    @Test
    public void validarTransacao_AltaFrequencia_DeveRetornarFalso() {
        adicionarTransacoes(3, LocalDateTime.now());

        Transacao transacao = criarTransacao();

        boolean resultado = antifraudeService.validarTransacao(cartao, transacao);

        assertFalse(resultado);
    }

    @Test
    public void validarTransacao_TransacaoDuplicada_DeveRetornarFalso() {
        adicionarTransacoes(2, LocalDateTime.now(), "Loja A", 100.0);

        Transacao transacao = criarTransacao();
        transacao.setComerciante("Loja A");
        transacao.setValor(100.0);

        boolean resultado = antifraudeService.validarTransacao(cartao, transacao);

        assertFalse(resultado);
    }

    @Test
    public void validarTransacao_TransacaoValida_DeveRetornarVerdadeiro() {
        adicionarTransacoes(1, LocalDateTime.now());

        Transacao transacao = criarTransacao();

        boolean resultado = antifraudeService.validarTransacao(cartao, transacao);

        assertTrue(resultado);
    }

    private Transacao criarTransacao() {
        Transacao transacao = new Transacao();
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setComerciante("Loja A");
        transacao.setValor(100.0);
        transacao.setDescricao("Compra Teste");
        transacao.setCartao(cartao);
        return transacao;
    }

    private void adicionarTransacoes(int quantidade, LocalDateTime dataHora) {
        for (int i = 0; i < quantidade; i++) {
            Transacao transacao = criarTransacao();
            transacao.setDtTransacao(dataHora.minusSeconds(i * 30)); // Diferente por 30 segundos
            cartao.getTransacoes().add(transacao);
        }
    }

    private void adicionarTransacoes(int quantidade, LocalDateTime dataHora, String comerciante, double valor) {
        for (int i = 0; i < quantidade; i++) {
            Transacao transacao = criarTransacao();
            transacao.setDtTransacao(dataHora.minusSeconds(i * 30)); // Diferente por 30 segundos
            transacao.setComerciante(comerciante);
            transacao.setValor(valor);
            cartao.getTransacoes().add(transacao);
        }
    }
}
