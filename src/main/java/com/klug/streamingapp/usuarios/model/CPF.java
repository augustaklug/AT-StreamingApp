package com.klug.streamingapp.usuarios.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.InputMismatchException;

@Getter
@NoArgsConstructor
@Embeddable
public class CPF {
    private String value;

    public CPF(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.value = value;
    }

    private boolean isValid(String cpf) {
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
