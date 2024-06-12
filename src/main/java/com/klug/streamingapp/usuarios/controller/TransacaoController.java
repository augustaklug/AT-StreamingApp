package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.TransacaoDTO;
import com.klug.streamingapp.usuarios.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/autorizar")
    public ResponseEntity<TransacaoDTO> autorizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            TransacaoDTO transacaoAutorizada = transacaoService.autorizarTransacao(transacaoDTO);
            return ResponseEntity.ok(transacaoAutorizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
