package com.klug.streamingapp.usuarios.domain;

import com.klug.streamingapp.conteudo.domain.Playlist;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
    private String cpf;
    private String email;
    @OneToMany
    private List<Cartao> cartoes = new ArrayList<>();
    @OneToMany
    private List<Playlist> playlists = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assinatura_id", referencedColumnName = "id")
    private Assinatura assinatura;

    @Transient
    private static final String DEFAULT_PLAYLIST_NAME = "Músicas Favoritas";

    public void criar(String nome, String email, String senha, String cpf, Cartao cartao, Plano plano) throws Exception {
        if (!isValidoCpf(cpf)) {
            throw new Exception("CPF está inválido");
        }

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;

        transacionarCartao(cartao, plano);
        adicionarAssinatura(plano);
        criarPlaylistDefault();
    }

    private void transacionarCartao(Cartao cartao, Plano plano) throws Exception {
        cartao.criarTransacao(plano.getNome(), plano.getValor(), plano.getDescricao());
        this.cartoes.add(cartao);
    }

    private void adicionarAssinatura(Plano plano) {
        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setDtAssinatura(LocalDateTime.now());
        assinatura.setAtivo(true);
        this.assinatura = assinatura;
    }

    private void criarPlaylistDefault() {
        Playlist playlist = new Playlist();
        playlist.setNome(DEFAULT_PLAYLIST_NAME);
        this.playlists.add(playlist);
    }

    private boolean isValidoCpf(String cpf) {
        if (cpf.matches("^(\\d)\\1*$") || cpf.length() != 11) {
            return false;
        }

        try {
            int dig10 = calcularDigitoVerificador(cpf, 9, 10);
            int dig11 = calcularDigitoVerificador(cpf, 10, 11);

            return dig10 == Character.getNumericValue(cpf.charAt(9)) &&
                   dig11 == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    private int calcularDigitoVerificador(String cpf, int length, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < length; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            soma += num * peso;
            peso--;
        }

        int resto = 11 - (soma % 11);
        return (resto == 10 || resto == 11) ? 0 : resto;
    }
}
