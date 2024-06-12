package com.klug.streamingapp.domain;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Plano;
import com.klug.streamingapp.usuarios.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class UsuarioTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Cartao criarCartaoAtivo() {
        Cartao cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setNumero("1234567890123456");
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(3));
        cartao.setLimite(1000.0);
        return cartao;
    }

    private Plano criarPlanoAtivo() {
        Plano plano = new Plano();
        plano.setId(UUID.randomUUID());
        plano.setNome("Plano Teste");
        plano.setAtivo(true);
        plano.setDescricao("Descrição do Plano Teste");
        plano.setValor(100.0);
        return plano;
    }

    @Test
    public void deve_criar_um_usuario_corretamente() throws Exception {
        Usuario usuario = new Usuario();
        Cartao cartao = criarCartaoAtivo();
        Plano plano = criarPlanoAtivo();

        String senhaCodificada = passwordEncoder.encode("123456");
        usuario.criar("Xpto", "teste@teste.com", senhaCodificada, "39048007011", cartao, plano);

        Assertions.assertEquals("39048007011", usuario.getCpf());
        Assertions.assertEquals("Xpto", usuario.getNome());
        Assertions.assertEquals("teste@teste.com", usuario.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("123456", usuario.getSenha()));
    }

    @Test
    public void nao_deve_criar_um_usuario_com_cpf_invalido() {
        Usuario usuario = new Usuario();
        Cartao cartao = criarCartaoAtivo();
        Plano plano = criarPlanoAtivo();

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            usuario.criar("Xpto", "teste@teste.com", "123456", "12345678901", cartao, plano);
        });

        String expectedMessage = "CPF está inválido";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
