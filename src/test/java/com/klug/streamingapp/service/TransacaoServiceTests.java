package com.klug.streamingapp.service;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Transacao;
import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.repository.CartaoRepository;
import com.klug.streamingapp.usuarios.repository.TransacaoRepository;
import com.klug.streamingapp.usuarios.service.AntifraudeService;
import com.klug.streamingapp.usuarios.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransacaoServiceTests {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private AntifraudeService antifraudeService;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Cartao criarCartaoAtivo() {
        Cartao cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setNumero("1234567890123456");
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(3));
        cartao.setLimite(1000.0);
        return cartao;
    }

    @Test
    public void deveAutorizarTransacaoCorretamente() throws Exception {
        UUID cartaoId = UUID.randomUUID();
        Cartao cartao = criarCartaoAtivo();
        cartao.setId(cartaoId);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setCartaoId(cartaoId);
        transacaoDTO.setValor(100.0);
        transacaoDTO.setMerchant("Teste");
        transacaoDTO.setDescricao("Descrição da Transação");

        when(cartaoRepository.findById(cartaoId)).thenReturn(Optional.of(cartao));
        when(antifraudeService.validarTransacao(any(Cartao.class), any(Transacao.class))).thenReturn(true);

        Transacao transacao = new Transacao();
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        transacao.setValor(100.0);
        transacao.setComerciante("Teste");
        transacao.setDescricao("Descrição da Transação");
        transacao.setCartao(cartao);

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        TransacaoDTO autorizada = transacaoService.autorizarTransacao(transacaoDTO);

        assertNotNull(autorizada.getId());
        assertEquals(transacaoDTO.getValor(), autorizada.getValor());
        assertEquals(transacaoDTO.getMerchant(), autorizada.getMerchant());
        assertEquals(transacaoDTO.getDescricao(), autorizada.getDescricao());

        verify(cartaoRepository, times(1)).findById(cartaoId);
        verify(antifraudeService, times(1)).validarTransacao(any(Cartao.class), any(Transacao.class));
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
    }
}
