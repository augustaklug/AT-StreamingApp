package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    private TransacaoDTO transacaoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        transacaoDTO = new TransacaoDTO();
        transacaoDTO.setId(UUID.randomUUID());
        transacaoDTO.setCartaoId(UUID.randomUUID());
        transacaoDTO.setValor(100.0);
        transacaoDTO.setMerchant("Teste Merchant");
        transacaoDTO.setDescricao("Teste Descrição");
        transacaoDTO.setDtTransacao(LocalDateTime.now());
    }

    @Test
    public void testAutorizarTransacao_Sucesso() throws Exception {
        when(transacaoService.autorizarTransacao(any(TransacaoDTO.class))).thenReturn(transacaoDTO);

        ResponseEntity<TransacaoDTO> response = transacaoController.autorizarTransacao(transacaoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoDTO, response.getBody());
    }

    @Test
    public void testAutorizarTransacao_CartaoInvalido() throws Exception {
        when(transacaoService.autorizarTransacao(any(TransacaoDTO.class))).thenThrow(new Exception("Cartão não é válido ou não está ativo."));

        ResponseEntity<TransacaoDTO> response = transacaoController.autorizarTransacao(transacaoDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAutorizarTransacao_TransacaoInvalida() throws Exception {
        when(transacaoService.autorizarTransacao(any(TransacaoDTO.class))).thenThrow(new Exception("Transação inválida"));

        ResponseEntity<TransacaoDTO> response = transacaoController.autorizarTransacao(transacaoDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
