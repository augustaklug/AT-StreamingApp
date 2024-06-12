package com.klug.streamingapp.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codigoAutorizacao;
    private double valor;
    private LocalDateTime dtTransacao;
    private String comerciante;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}
