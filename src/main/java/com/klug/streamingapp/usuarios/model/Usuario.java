package com.klug.streamingapp.usuarios.model;

import com.klug.streamingapp.conteudo.model.Playlist;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String senha;
    @Embedded
    private CPF cpf;
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "assinatura_id", referencedColumnName = "id")
    private Assinatura assinatura;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, CPF cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }
}
