package com.klug.streamingapp.conteudo.service;


import com.klug.streamingapp.conteudo.domain.Musica;
import com.klug.streamingapp.conteudo.domain.Playlist;
import com.klug.streamingapp.conteudo.dto.FavoritarMusicaDTO;
import com.klug.streamingapp.conteudo.repository.MusicaRepository;
import com.klug.streamingapp.usuarios.domain.Usuario;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public void favoritarMusica(FavoritarMusicaDTO favoritarMusicaDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(favoritarMusicaDTO.getUsuarioId())
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        Musica musica = musicaRepository.findById(favoritarMusicaDTO.getMusicaId())
                .orElseThrow(() -> new Exception("Música não encontrada"));

        Playlist playlistFavoritas = usuario.getPlaylists().stream()
                .filter(playlist -> playlist.getNome().equals("Músicas Favoritas"))
                .findFirst()
                .orElseThrow(() -> new Exception("Playlist de músicas favoritas não encontrada"));

        playlistFavoritas.getMusicas().add(musica);
        usuarioRepository.save(usuario);
    }
}
