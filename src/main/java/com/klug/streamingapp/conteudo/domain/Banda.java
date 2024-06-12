package com.klug.streamingapp.conteudo.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Banda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String descricao;
    @OneToMany
    private List<Musica> musicas;
}
