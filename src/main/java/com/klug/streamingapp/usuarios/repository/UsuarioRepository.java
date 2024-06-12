package com.klug.streamingapp.usuarios.repository;


import com.klug.streamingapp.conteudo.model.Playlist;
import com.klug.streamingapp.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Playlist> findPlaylistById(UUID playlistId);
}
