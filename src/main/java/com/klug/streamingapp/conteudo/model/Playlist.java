package com.klug.streamingapp.conteudo.model;


import com.klug.streamingapp.usuarios.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;

    @ManyToMany
    private List<Musica> musicas = new ArrayList<>();

    @ManyToOne
    private Usuario usuario;

    public Playlist(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
    }

    public void adicionarMusica(Musica musica) {
        this.musicas.add(musica);
    }

    public void removerMusica(Musica musica) {
        this.musicas.remove(musica);
    }
}
