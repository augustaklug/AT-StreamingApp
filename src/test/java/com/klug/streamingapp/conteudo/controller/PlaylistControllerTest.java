package com.klug.streamingapp.conteudo.controller;

import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.dto.PlaylistDTO;
import com.klug.streamingapp.conteudo.model.Playlist;
import com.klug.streamingapp.conteudo.service.PlaylistService;
import com.klug.streamingapp.usuarios.model.Usuario;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
    }

    @Test
    public void criarPlaylist_DeveRetornarPlaylistCriada() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Playlist playlist = new Playlist("Minha Playlist", usuario);
        PlaylistDTO playlistDTO = new PlaylistDTO(playlist);

        when(playlistService.criarPlaylist(any(PlaylistDTO.class))).thenReturn(playlistDTO);

        mockMvc.perform(post("/api/v1/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Minha Playlist\",\"usuarioId\":\"" + usuarioId + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Minha Playlist"));
    }

    @Test
    public void adicionarMusica_DeveRetornarStatusOk() throws Exception {
        UUID playlistId = UUID.randomUUID();
        UUID musicaId = UUID.randomUUID();

        doNothing().when(playlistService).adicionarMusica(playlistId, musicaId);

        mockMvc.perform(post("/api/v1/playlists/" + playlistId + "/musicas/" + musicaId))
                .andExpect(status().isOk());

        verify(playlistService, times(1)).adicionarMusica(playlistId, musicaId);
    }

    @Test
    public void removerMusica_DeveRetornarStatusOk() throws Exception {
        UUID playlistId = UUID.randomUUID();
        UUID musicaId = UUID.randomUUID();

        doNothing().when(playlistService).removerMusica(playlistId, musicaId);

        mockMvc.perform(delete("/api/v1/playlists/" + playlistId + "/musicas/" + musicaId))
                .andExpect(status().isOk());

        verify(playlistService, times(1)).removerMusica(playlistId, musicaId);
    }

    @Test
    public void favoritarMusica_DeveRetornarStatusOk() throws Exception {
        FavoritarMusicaDTO favoritarMusicaDTO = new FavoritarMusicaDTO();
        favoritarMusicaDTO.setUsuarioId(UUID.randomUUID());
        favoritarMusicaDTO.setMusicaId(UUID.randomUUID());

        doNothing().when(playlistService).favoritarMusica(any(FavoritarMusicaDTO.class));

        mockMvc.perform(post("/api/v1/playlists/favoritar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\":\"" + favoritarMusicaDTO.getUsuarioId() + "\",\"musicaId\":\"" + favoritarMusicaDTO.getMusicaId() + "\"}"))
                .andExpect(status().isOk());

        verify(playlistService, times(1)).favoritarMusica(any(FavoritarMusicaDTO.class));
    }
}