package com.klug.streamingapp.domain;

import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class CartaoTests {

    @Test
    public void deve_criar_transacao_com_sucesso() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setAtivo(true);
        cartao.setLimite(1000);
        cartao.setNumero("5300419483501944");
        cartao.setValidade(LocalDate.now().plusYears(1));

        cartao.criarTransacao("Xpto", 100, "Transacao de Teste");

        Assertions.assertTrue(cartao.getTransacoes().size() > 0);
        Assertions.assertTrue(cartao.getLimite() == 900);
    }

    @Test
    public void nao_deve_criar_transacao_caso_cartao_esteja_inativo() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setAtivo(false);
        cartao.setLimite(1000);
        cartao.setNumero("5300419483501944");
        cartao.setValidade(LocalDate.now().plusYears(1));

        Assertions.assertThrows(Exception.class, () ->{
            cartao.criarTransacao("Xpto", 100, "Transacao de Teste");
        });

    }

    @Test
    public void nao_deve_criar_transacao_caso_cartao_sem_limite() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setAtivo(true);
        cartao.setLimite(50);
        cartao.setNumero("5300419483501944");
        cartao.setValidade(LocalDate.now().plusYears(1));

        Assertions.assertThrows(Exception.class, () ->{
            cartao.criarTransacao("Xpto", 100, "Transacao de Teste");
        });

    }

    @Test
    public void nao_deve_criar_transacao_caso_seja_alta_frequencia() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setAtivo(true);
        cartao.setLimite(1000);
        cartao.setNumero("5300419483501944");
        cartao.setValidade(LocalDate.now().plusYears(1));

        Transacao transacao1 = new Transacao();
        transacao1.setDtTransacao(LocalDateTime.now().minusSeconds(30));
        transacao1.setValor(50);
        transacao1.setComerciante("dummy1");
        cartao.getTransacoes().add(transacao1);

        Transacao transacao2 = new Transacao();
        transacao2.setDtTransacao(LocalDateTime.now().minusSeconds(45));
        transacao2.setValor(50);
        transacao2.setComerciante("dummy2");
        cartao.getTransacoes().add(transacao2);

        Transacao transacao3 = new Transacao();
        transacao3.setDtTransacao(LocalDateTime.now().minusSeconds(60));
        transacao3.setValor(50);
        transacao3.setComerciante("dummy3");
        cartao.getTransacoes().add(transacao3);

        Assertions.assertThrows(Exception.class, () ->{
            cartao.criarTransacao("Xpto", 100, "Transacao de Teste");
        });

    }

    @Test
    public void nao_deve_criar_transacao_caso_seja_repetidas() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setAtivo(true);
        cartao.setLimite(1000);
        cartao.setNumero("5300419483501944");
        cartao.setValidade(LocalDate.now().plusYears(1));

        Transacao transacao1 = new Transacao();
        transacao1.setDtTransacao(LocalDateTime.now().minusSeconds(30));
        transacao1.setValor(50);
        transacao1.setComerciante("dummy1");
        cartao.getTransacoes().add(transacao1);

        Transacao transacao2 = new Transacao();
        transacao2.setDtTransacao(LocalDateTime.now().minusSeconds(45));
        transacao2.setValor(50);
        transacao2.setComerciante("dummy1");
        cartao.getTransacoes().add(transacao2);

        Assertions.assertThrows(Exception.class, () ->{
            cartao.criarTransacao("dummy1", 50, "Transacao de Teste");
        });

    }

}
