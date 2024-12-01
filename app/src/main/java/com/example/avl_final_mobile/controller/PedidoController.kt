package com.example.avl_final_mobile.controller

import com.example.avl_final_mobile.model.ItensCardapio
import com.example.avl_final_mobile.model.Pedido
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PedidoController {
    private val db = FirebaseFirestore.getInstance()

    fun adicionarOuAtualizarPedido(usuarioNome: String?, novoItem: ItensCardapio, onResult: (Boolean, String?) -> Unit) {
        val colecao = db.collection("pedidos")

        colecao.whereEqualTo("usuarioNome", usuarioNome)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.documents.isNotEmpty()) {
                    val documento = querySnapshot.documents[0]
                    val pedidoExistente = documento.toObject(Pedido::class.java)

                    pedidoExistente?.itens?.add(novoItem)

                    documento.reference.set(pedidoExistente!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                onResult(true, null)
                            else
                                onResult(false, task.exception?.message)
                        }
                } else {
                    val novoPedido = Pedido(usuarioNome = usuarioNome, itens = mutableListOf(novoItem))
                    colecao.add(novoPedido)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                onResult(true, null)
                            else
                                onResult(false, task.exception?.message)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.message)
            }
    }

    fun buscarPedido(): Query {
        return db.collection("pedidos")
    }
}