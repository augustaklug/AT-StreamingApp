package com.klug.streamingapp.usuarios.dto;


import com.klug.streamingapp.usuarios.model.Usuario;
import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioDTO {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.cpf = String.valueOf(usuario.getCpf());
    }
}
