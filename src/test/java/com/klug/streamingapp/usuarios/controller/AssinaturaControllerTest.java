package com.klug.streamingapp.usuarios.controller;

import com.klug.streamingapp.usuarios.dto.AssinaturaDTO;
import com.klug.streamingapp.usuarios.service.AssinaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class AssinaturaControllerTest {

    @InjectMocks
    private AssinaturaController assinaturaController;

    @Mock
    private AssinaturaService assinaturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarAssinaturaComSucesso() throws Exception {
        // Arrange
        AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
        assinaturaDTO.setUsuarioId(UUID.randomUUID());
        assinaturaDTO.setPlanoId(UUID.randomUUID());

        AssinaturaDTO novaAssinatura = new AssinaturaDTO();
        novaAssinatura.setId(UUID.randomUUID());
        novaAssinatura.setUsuarioId(assinaturaDTO.getUsuarioId());
        novaAssinatura.setPlanoId(assinaturaDTO.getPlanoId());

        when(assinaturaService.criarAssinatura(any(AssinaturaDTO.class))).thenReturn(novaAssinatura);

        // Act
        ResponseEntity<AssinaturaDTO> response = assinaturaController.criarAssinatura(assinaturaDTO);

        // Assert
        assertEquals(ResponseEntity.ok(novaAssinatura), response);
    }

    @Test
    void testCriarAssinaturaUsuarioJaPossuiAssinaturaAtiva() throws Exception {
        // Arrange
        AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
        assinaturaDTO.setUsuarioId(UUID.randomUUID());
        assinaturaDTO.setPlanoId(UUID.randomUUID());

        when(assinaturaService.criarAssinatura(any(AssinaturaDTO.class))).thenThrow(new Exception("Usuário já possui uma assinatura ativa."));

        // Act
        ResponseEntity<AssinaturaDTO> response = assinaturaController.criarAssinatura(assinaturaDTO);

        // Assert
        assertEquals(ResponseEntity.badRequest().body(null), response);
    }

    @Test
    void testCriarAssinaturaPlanoNaoEncontrado() throws Exception {
        // Arrange
        AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
        assinaturaDTO.setUsuarioId(UUID.randomUUID());
        assinaturaDTO.setPlanoId(UUID.randomUUID());

        when(assinaturaService.criarAssinatura(any(AssinaturaDTO.class))).thenThrow(new Exception("Plano não encontrado"));

        // Act
        ResponseEntity<AssinaturaDTO> response = assinaturaController.criarAssinatura(assinaturaDTO);

        // Assert
        assertEquals(ResponseEntity.badRequest().body(null), response);
    }
}
