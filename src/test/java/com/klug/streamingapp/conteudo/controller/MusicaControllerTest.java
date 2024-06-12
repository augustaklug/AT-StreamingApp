package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.model.Musica;
import com.klug.streamingapp.conteudo.service.MusicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MusicaControllerTest {

    @Mock
    private MusicaService musicaService;

    @InjectMocks
    private MusicaController musicaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(musicaController).build();
    }

    @Test
    public void obterMusica_DeveRetornarMusica() throws Exception {
        UUID musicaId = UUID.randomUUID();
        Musica musica = new Musica();
        musica.setId(musicaId);
        musica.setNome("Minha Musica");
        musica.setDuracao(300);

        when(musicaService.obterMusica(any(UUID.class))).thenReturn(musica);

        mockMvc.perform(get("/api/v1/musicas/" + musicaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Minha Musica"))
                .andExpect(jsonPath("$.duracao").value(300));
    }
}