package com.automacao.lua.dto;

public enum condicoes {
    IGUAL(1),
    MENOR(2),
    MAIOR(3),
    MENOR_IGUAL(4),
    MAIOR_IGUAL(5);

    private int valor;

    condicoes(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }
}
