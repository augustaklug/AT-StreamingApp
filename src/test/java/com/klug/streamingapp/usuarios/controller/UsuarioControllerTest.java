package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO usuarioDTO;
    private UUID cartaoId;
    private UUID planoId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao.silva@example.com");
        usuarioDTO.setSenha("senhaSegura123");
        usuarioDTO.setCpf("12345678901");

        cartaoId = UUID.randomUUID();
        planoId = UUID.randomUUID();
    }

    @Test
    public void testCriarContaSucesso() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(UUID.class), any(UUID.class))).thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartaoId, planoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarioDTO, response.getBody());
    }

    @Test
    public void testCriarContaUsuarioJaPossuiAssinaturaAtiva() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(UUID.class), any(UUID.class))).thenThrow(new Exception("Usuário já possui uma assinatura ativa."));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartaoId, planoId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCriarContaCartaoInvalido() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(UUID.class), any(UUID.class))).thenThrow(new Exception("Cartão não é válido ou não está ativo."));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartaoId, planoId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCriarContaPlanoNaoEncontrado() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(UUID.class), any(UUID.class))).thenThrow(new Exception("Plano não encontrado"));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartaoId, planoId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }
}