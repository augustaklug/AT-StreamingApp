package com.klug.streamingapp.usuarios.controller;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Plano;
import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarConta(@RequestBody UsuarioDTO usuarioDTO, @RequestBody Cartao cartao, @RequestBody Plano plano) {
        try {
            UsuarioDTO createdUsuario = usuarioService.criarConta(usuarioDTO, cartao, plano);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}