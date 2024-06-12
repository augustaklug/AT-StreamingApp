package com.klug.streamingapp.service;


import com.klug.streamingapp.conteudo.domain.Musica;
import com.klug.streamingapp.conteudo.domain.Playlist;
import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.repository.MusicaRepository;
import com.klug.streamingapp.conteudo.service.MusicaService;
import com.klug.streamingapp.usuarios.domain.Usuario;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MusicaServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MusicaRepository musicaRepository;

    @InjectMocks
    private MusicaService musicaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario criarUsuarioComPlaylist() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        Playlist playlist = new Playlist();
        playlist.setId(UUID.randomUUID());
        playlist.setNome("Músicas Favoritas");
        playlist.setMusicas(new ArrayList<>());
        usuario.getPlaylists().add(playlist);
        return usuario;
    }

    private Musica criarMusica() {
        Musica musica = new Musica();
        musica.setId(UUID.randomUUID());
        musica.setNome("Musica Teste");
        return musica;
    }

    @Test
    public void deveFavoritarMusicaCorretamente() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        UUID musicaId = UUID.randomUUID();

        Usuario usuario = criarUsuarioComPlaylist();
        Musica musica = criarMusica();

        FavoritarMusicaDTO favoritarMusicaDTO = new FavoritarMusicaDTO();
        favoritarMusicaDTO.setUsuarioId(usuarioId);
        favoritarMusicaDTO.setMusicaId(musicaId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(musicaRepository.findById(musicaId)).thenReturn(Optional.of(musica));

        musicaService.favoritarMusica(favoritarMusicaDTO);

        assertTrue(usuario.getPlaylists().get(0).getMusicas().contains(musica));

        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(musicaRepository, times(1)).findById(musicaId);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}