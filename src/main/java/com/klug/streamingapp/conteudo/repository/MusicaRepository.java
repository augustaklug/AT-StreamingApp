package com.klug.streamingapp.conteudo.repository;

import com.klug.streamingapp.conteudo.domain.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, UUID> {
}
