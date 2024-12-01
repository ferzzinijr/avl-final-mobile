package com.example.avl_final_mobile.model

class Pedido {
    var usuarioNome: String? = null
    var itens: MutableList<ItensCardapio> = mutableListOf()

    constructor()
    constructor(usuarioNome: String?, itens: MutableList<ItensCardapio>) {
        this.usuarioNome = usuarioNome
        this.itens = itens
    }
}