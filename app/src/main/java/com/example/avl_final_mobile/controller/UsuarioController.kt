package com.example.avl_final_mobile.controller

import com.example.avl_final_mobile.model.Usuario
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioController {
    private val db = FirebaseFirestore.getInstance()
    private val ctrl = AutenticacaoController()

    fun adicionar(usuario: Usuario) {
        val colecao = db.collection("usuarios")
        colecao.add(usuario)
    }

    fun nomeUsuario(onResult: (String?) -> Unit) {
        val emailUsuario = ctrl.emailUsuario()
        val colecao = db.collection("usuarios")

        colecao.whereEqualTo("email", emailUsuario)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val usuario = querySnapshot.documents[0]
                    val nome = usuario.getString("nome")
                    onResult(nome)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                onResult(null)
                exception.printStackTrace()
            }
    }
}