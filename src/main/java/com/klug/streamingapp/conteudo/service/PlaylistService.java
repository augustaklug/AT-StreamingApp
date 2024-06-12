package com.klug.streamingapp.conteudo.service;

import com.klug.streamingapp.conteudo.model.Musica;
import com.klug.streamingapp.conteudo.model.Playlist;
import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.repository.MusicaRepository;
import com.klug.streamingapp.usuarios.model.Usuario;
import com.klug.streamingapp.conteudo.dto.PlaylistDTO;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlaylistService {
    private static final String DEFAULT_PLAYLIST_NAME = "Músicas Favoritas";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public PlaylistDTO criarPlaylist(PlaylistDTO playlistDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(playlistDTO.getUsuarioId()).orElseThrow(() -> new Exception("Usuário não encontrado"));

        Playlist playlist = new Playlist(playlistDTO.getNome(), usuario);
        usuario.getPlaylists().add(playlist);

        usuarioRepository.save(usuario);

        return new PlaylistDTO(playlist);
    }

    public void criarPlaylistDefault(Usuario usuario) throws Exception {
        Playlist playlist = new Playlist(DEFAULT_PLAYLIST_NAME, usuario);
        usuario.getPlaylists().add(playlist);
        usuarioRepository.save(usuario);
    }

    public void adicionarMusica(UUID playlistId, UUID musicaId) throws Exception {
        Playlist playlist = usuarioRepository.findPlaylistById(playlistId).orElseThrow(() -> new Exception("Playlist não encontrada"));

        Musica musica = musicaRepository.findById(musicaId).orElseThrow(() -> new Exception("Música não encontrada"));

        playlist.adicionarMusica(musica);
        usuarioRepository.save(playlist.getUsuario());
    }

    public void removerMusica(UUID playlistId, UUID musicaId) throws Exception {
        Playlist playlist = usuarioRepository.findPlaylistById(playlistId).orElseThrow(() -> new Exception("Playlist não encontrada"));

        Musica musica = musicaRepository.findById(musicaId).orElseThrow(() -> new Exception("Música não encontrada"));

        playlist.removerMusica(musica);
        usuarioRepository.save(playlist.getUsuario());
    }

    public void favoritarMusica(FavoritarMusicaDTO favoritarMusicaDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(favoritarMusicaDTO.getUsuarioId()).orElseThrow(() -> new Exception("Usuário não encontrado"));

        Musica musica = musicaRepository.findById(favoritarMusicaDTO.getMusicaId()).orElseThrow(() -> new Exception("Música não encontrada"));

        Playlist playlistFavoritas = usuario.getPlaylists().stream().filter(playlist -> playlist.getNome().equals("Músicas Favoritas")).findFirst().orElseThrow(() -> new Exception("Playlist de músicas favoritas não encontrada"));

        playlistFavoritas.adicionarMusica(musica);
        usuarioRepository.save(usuario);
    }
}
