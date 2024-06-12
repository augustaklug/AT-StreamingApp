package com.klug.streamingapp.usuarios.controller;


import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Plano;
import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criarConta")
    public ResponseEntity<UsuarioDTO> criarConta(@RequestBody UsuarioDTO usuarioDTO, @RequestParam UUID cartaoId, @RequestParam UUID planoId) {
        try {
            Cartao cartao = new Cartao(); // Supondo que o cartão é criado na mesma requisição
            cartao.setId(cartaoId);

            Plano plano = new Plano(); // Supondo que o plano é criado na mesma requisição
            plano.setId(planoId);

            UsuarioDTO novoUsuario = usuarioService.criarConta(usuarioDTO, cartao, plano);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}