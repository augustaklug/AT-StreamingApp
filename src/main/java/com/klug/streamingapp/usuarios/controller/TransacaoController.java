package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<TransacaoDTO> autorizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            TransacaoDTO autorizada = transacaoService.autorizarTransacao(transacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(autorizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
