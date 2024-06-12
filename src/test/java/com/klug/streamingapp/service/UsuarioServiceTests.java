package com.klug.streamingapp.service;


import com.klug.streamingapp.usuarios.domain.Cartao;
import com.klug.streamingapp.usuarios.domain.Plano;
import com.klug.streamingapp.usuarios.domain.Usuario;
import com.klug.streamingapp.usuarios.dto.UsuarioDTO;
import com.klug.streamingapp.usuarios.repository.UsuarioRepository;
import com.klug.streamingapp.usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
    public void deveCriarContaCorretamente() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Xpto");
        usuarioDTO.setEmail("teste@teste.com");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setCpf("39048007011");

        String senhaCodificada = passwordEncoder.encode(usuarioDTO.getSenha());

        Cartao cartao = criarCartaoAtivo();
        Plano plano = criarPlanoAtivo();

        Usuario usuario = new Usuario();
        usuario.criar(usuarioDTO.getNome(), usuarioDTO.getEmail(), senhaCodificada, usuarioDTO.getCpf(), cartao, plano);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO createdUsuario = usuarioService.criarConta(usuarioDTO, cartao, plano);

        assertEquals(usuarioDTO.getNome(), createdUsuario.getNome());
        assertEquals(usuarioDTO.getEmail(), createdUsuario.getEmail());
        assertEquals(usuarioDTO.getCpf(), createdUsuario.getCpf());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void naoDeveCriarContaComCpfInvalido() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Xpto");
        usuarioDTO.setEmail("teste@teste.com");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setCpf("12345678901");

        Cartao cartao = criarCartaoAtivo();
        Plano plano = criarPlanoAtivo();

        Usuario usuario = new Usuario();

        Exception exception = assertThrows(Exception.class, () -> {
            usuario.criar(usuarioDTO.getNome(), usuarioDTO.getEmail(), "123456", usuarioDTO.getCpf(), cartao, plano);
        });

        String expectedMessage = "CPF está inválido";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
