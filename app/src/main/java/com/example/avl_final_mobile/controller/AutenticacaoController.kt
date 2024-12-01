package com.example.avl_final_mobile.controller

import com.google.firebase.auth.FirebaseAuth

class AutenticacaoController {
    fun login(email: String, senha: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }

    fun criarUsuario(nome: String, email: String, senha: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }

    fun esqueceuSenha(email: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun usuarioAutenticado(): String? {
        val auth = FirebaseAuth.getInstance()

        return auth.currentUser?.email.toString()
    }

    fun idUsuario(): String? {
        val auth = FirebaseAuth.getInstance()

        return auth.currentUser?.uid.toString()
    }

    fun emailUsuario(): String? {
        val auth = FirebaseAuth.getInstance()

        return auth.currentUser?.email.toString()
    }
}