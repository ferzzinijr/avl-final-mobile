package com.example.avl_final_mobile.model

class ItensCardapio {
    var id: String? = null
    var uid: String? = null
    var imagem: String? = null
    var nome: String? = null
    var descricao: String? = null
    var preco: String? = null

    constructor()
    constructor(id: String?, uid: String?, imagem: String?, nome: String?, descricao: String?, preco: String?) {
        this.id = id
        this.uid = uid
        this.imagem = imagem
        this.nome = nome
        this.descricao = descricao
        this.preco = preco
    }
}