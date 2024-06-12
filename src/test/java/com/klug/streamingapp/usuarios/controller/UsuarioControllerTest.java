package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.model.Cartao;
import com.klug.streamingapp.usuarios.model.Plano;
import com.klug.streamingapp.usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
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
    private Cartao cartao;
    private Plano plano;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao.silva@example.com");
        usuarioDTO.setSenha("senhaSegura123");
        usuarioDTO.setCpf("12345678901");

        cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setNumero("1234567812345678");
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(2));
        cartao.setLimite(5000.00);

        plano = new Plano();
        plano.setId(UUID.randomUUID());
        plano.setNome("Plano Premium");
        plano.setAtivo(true);
        plano.setDescricao("Acesso ilimitado a todas as músicas");
        plano.setValor(29.90);
    }

    @Test
    public void testCriarContaSucesso() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(Cartao.class), any(Plano.class)))
                .thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartao.getId(), plano.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarioDTO, response.getBody());
    }

    @Test
    public void testCriarContaUsuarioJaPossuiAssinaturaAtiva() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(Cartao.class), any(Plano.class)))
                .thenThrow(new Exception("Usuário já possui uma assinatura ativa."));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartao.getId(), plano.getId());

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCriarContaCartaoInvalido() throws Exception {
        cartao.setAtivo(false);
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(Cartao.class), any(Plano.class)))
                .thenThrow(new Exception("Cartão não é válido ou não está ativo."));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartao.getId(), plano.getId());

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testCriarContaPlanoNaoEncontrado() throws Exception {
        when(usuarioService.criarConta(any(UsuarioDTO.class), any(Cartao.class), any(Plano.class)))
                .thenThrow(new Exception("Plano não encontrado"));

        ResponseEntity<UsuarioDTO> response = usuarioController.criarConta(usuarioDTO, cartao.getId(), plano.getId());

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }
}
