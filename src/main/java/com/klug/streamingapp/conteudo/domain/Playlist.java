package com.klug.streamingapp.conteudo.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    @ManyToMany
    private List<Musica> musicas = new ArrayList<>();
}
