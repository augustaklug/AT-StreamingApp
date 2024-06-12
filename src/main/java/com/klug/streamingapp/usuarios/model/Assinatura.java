package com.klug.streamingapp.usuarios.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
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

    public Assinatura(Plano plano) {
        this.plano = plano;
        this.dtAssinatura = LocalDateTime.now();
        this.ativo = true;
    }
}
