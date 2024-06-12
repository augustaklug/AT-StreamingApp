package com.klug.streamingapp.conteudo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private int duracao;
    @ManyToOne
    private Banda banda;
}
