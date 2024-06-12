package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.AssinaturaDTO;
import com.klug.streamingapp.usuarios.service.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assinaturas")
public class AssinaturaController {

    @Autowired
    private AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<AssinaturaDTO> criarAssinatura(@RequestBody AssinaturaDTO assinaturaDTO) {
        try {
            AssinaturaDTO novaAssinatura = assinaturaService.criarAssinatura(assinaturaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaAssinatura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}