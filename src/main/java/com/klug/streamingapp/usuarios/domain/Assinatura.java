package com.klug.streamingapp.usuarios.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private boolean ativo;
    private LocalDateTime dtAssinatura;
    @ManyToOne
    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    private Plano plano;

    @OneToOne(mappedBy = "assinatura")
    private Usuario usuario;
}
