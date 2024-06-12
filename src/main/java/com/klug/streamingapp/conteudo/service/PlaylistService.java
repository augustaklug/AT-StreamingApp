package com.klug.streamingapp.conteudo.service;

import com.klug.streamingapp.conteudo.domain.Playlist;
import com.klug.streamingapp.usuarios.domain.Usuario;
import com.klug.streamingapp.conteudo.dto.PlaylistDTO;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PlaylistDTO criarPlaylist(PlaylistDTO playlistDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(playlistDTO.getUsuarioId())
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        Playlist playlist = new Playlist();
        playlist.setNome(playlistDTO.getNome());
        usuario.getPlaylists().add(playlist);
        usuarioRepository.save(usuario);

        return new PlaylistDTO(playlist);
    }
}
