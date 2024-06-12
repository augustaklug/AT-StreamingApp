package com.klug.streamingapp.usuarios.service;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Transacao;
import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.repository.CartaoRepository;
import com.klug.streamingapp.usuarios.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AntifraudeService antifraudeService;

    public TransacaoDTO autorizarTransacao(TransacaoDTO transacaoDTO) throws Exception {
        Cartao cartao = cartaoRepository.findById(transacaoDTO.getCartaoId())
                .orElseThrow(() -> new Exception("Cartão não encontrado"));

        Transacao transacao = new Transacao();
        transacao.setComerciante(transacaoDTO.getMerchant());
        transacao.setDescricao(transacaoDTO.getDescricao());
        transacao.setValor(transacaoDTO.getValor());
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(cartao);

        if (!antifraudeService.validarTransacao(cartao, transacao)) {
            throw new Exception("Transação inválida");
        }

        cartao.criarTransacao(transacaoDTO.getMerchant(), transacaoDTO.getValor(), transacaoDTO.getDescricao());
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return new TransacaoDTO(transacaoSalva);
    }
}
