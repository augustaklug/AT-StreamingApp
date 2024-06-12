package com.klug.streamingapp.usuarios.service;

import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.events.TransacaoCriadaEvent;
import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Transacao;
import com.klug.streamingapp.usuarios.repository.CartaoRepository;
import com.klug.streamingapp.usuarios.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransacaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private AntifraudeService antifraudeService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TransacaoService transacaoService;

    private Cartao cartao;
    private TransacaoDTO transacaoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusDays(1080)); // Cartão válido
        cartao.setTransacoes(new ArrayList<>());
        cartao.setLimite(1000.0);

        transacaoDTO = new TransacaoDTO();
        transacaoDTO.setCartaoId(cartao.getId());
        transacaoDTO.setMerchant("Loja A");
        transacaoDTO.setValor(100.0);
        transacaoDTO.setDescricao("Compra Teste");
        transacaoDTO.setDtTransacao(LocalDateTime.now());
    }

    @Test
    public void autorizarTransacao_CartaoNaoEncontrado_DeveLancarExcecao() {
        when(cartaoRepository.findById(transacaoDTO.getCartaoId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.autorizarTransacao(transacaoDTO);
        });

        assertEquals("Cartão não encontrado", exception.getMessage());
    }

    @Test
    public void autorizarTransacao_CartaoInvalido_DeveLancarExcecao() {
        cartao.setAtivo(false);
        when(cartaoRepository.findById(transacaoDTO.getCartaoId())).thenReturn(Optional.of(cartao));

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.autorizarTransacao(transacaoDTO);
        });

        assertEquals("Cartão não é válido ou não está ativo.", exception.getMessage());
    }

    @Test
    public void autorizarTransacao_TransacaoInvalida_DeveLancarExcecao() {
        when(cartaoRepository.findById(transacaoDTO.getCartaoId())).thenReturn(Optional.of(cartao));
        when(antifraudeService.validarTransacao(any(Cartao.class), any(Transacao.class))).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.autorizarTransacao(transacaoDTO);
        });

        assertEquals("Transação inválida", exception.getMessage());
    }

    @Test
    public void autorizarTransacao_TransacaoValida_DeveSalvarTransacaoEPublicarEvento() throws Exception {
        when(cartaoRepository.findById(transacaoDTO.getCartaoId())).thenReturn(Optional.of(cartao));
        when(antifraudeService.validarTransacao(any(Cartao.class), any(Transacao.class))).thenReturn(true);

        Transacao transacao = new Transacao();
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        transacao.setCartao(cartao);
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setComerciante(transacaoDTO.getMerchant());
        transacao.setDescricao(transacaoDTO.getDescricao());

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        TransacaoDTO resultado = transacaoService.autorizarTransacao(transacaoDTO);

        assertNotNull(resultado);
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
        verify(eventPublisher, times(1)).publishEvent(any(TransacaoCriadaEvent.class));
    }
}
