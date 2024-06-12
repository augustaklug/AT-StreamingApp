package com.klug.streamingapp.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String numero;
    private Boolean ativo;
    private LocalDate validade;
    private double limite;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Transacao> transacoes = new ArrayList<>();

    // Definição de constantes
    @Transient
    private final int TRANSACAO_INTERVALO_TEMPO = 2;
    @Transient
    private final int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    @Transient
    private final int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public void criarTransacao(String merchant, double valor, String descricao) throws Exception {

        validarCartao();

        Transacao transacao = new Transacao();
        transacao.setComerciante(merchant);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(this);

        validarLimite(transacao);

        if (!validarTransacao(transacao)) {
            throw new Exception("Transação inválida");
        }

        this.setLimite(this.getLimite() - transacao.getValor());
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        this.transacoes.add(transacao);
    }


    public void validarCartao() throws Exception {
        if (!this.ativo || this.validade.isBefore(LocalDate.now())) {
            throw new Exception("Cartão não é válido ou não está ativo.");
        }
    }

    private void validarLimite(Transacao transacao) throws Exception {
        if (this.limite < transacao.getValor()) {
            throw new Exception("Cartão não possui limite para esta transação");
        }
    }

    private boolean validarTransacao(Transacao transacao) {
        List<Transacao> ultimasTransacoes = this.getTransacoes().stream().filter((x) -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO))).toList();

        if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA) return false;

        List<Transacao> transacoesMerchantRepetidas = ultimasTransacoes.stream().filter((x) -> x.getComerciante().equals(transacao.getComerciante()) && x.getValor() == transacao.getValor()).toList();

        return transacoesMerchantRepetidas.size() < this.TRANSACAO_MERCHANT_DUPLICADAS;
    }

}
