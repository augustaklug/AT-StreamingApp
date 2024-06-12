package com.klug.streamingapp.usuarios.service;

import com.klug.streamingapp.usuarios.events.TransacaoCriadaEvent;
import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Transacao;
import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.repository.CartaoRepository;
import com.klug.streamingapp.usuarios.repository.TransacaoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AntifraudeService antifraudeService;

    public TransacaoDTO autorizarTransacao(TransacaoDTO transacaoDTO) throws Exception {
        Cartao cartao = cartaoRepository.findById(transacaoDTO.getCartaoId()).orElseThrow(() -> new Exception("Cartão não encontrado"));

        // Verificar se o cartão é válido e está ativo
        if (!cartao.getAtivo() || cartao.getValidade().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new Exception("Cartão não é válido ou não está ativo.");
        }

        Transacao transacao = new Transacao();
        transacao.setComerciante(transacaoDTO.getMerchant());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(cartao);

        // Validar transação com o serviço de antifraude
        if (!antifraudeService.validarTransacao(cartao, transacao)) {
            throw new Exception("Transação inválida");
        }

        cartao.criarTransacao(transacaoDTO.getMerchant(), transacaoDTO.getValor(), transacaoDTO.getDescricao());
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        // Publicar evento de domínio
        eventPublisher.publishEvent(new TransacaoCriadaEvent(transacaoSalva.getCodigoAutorizacao(), transacaoSalva.getValor(), transacaoSalva.getDtTransacao()));

        return new TransacaoDTO(transacaoSalva);
    }
}