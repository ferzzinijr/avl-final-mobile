package com.example.avl_final_mobile.controller

import com.example.avl_final_mobile.model.ItensCardapio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CardapioController {
    private val db = FirebaseFirestore.getInstance()

    fun listarItens(): Query {
        return db.collection("itens")
    }
}