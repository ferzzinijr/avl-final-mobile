package com.example.avl_final_mobile.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.controller.PedidoAdapter
import com.example.avl_final_mobile.controller.PedidoController
import com.example.avl_final_mobile.databinding.ActivityPedidoBinding
import com.example.avl_final_mobile.model.ItensCardapio
import com.example.avl_final_mobile.model.Pedido
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.getField

class PedidoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPedidoBinding
    private lateinit var ctrl: AutenticacaoController

    private lateinit var adapter: PedidoAdapter
    private var lista = mutableListOf<Pedido>()
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PedidoAdapter(lista)
        binding.rvItensPedido.layoutManager = LinearLayoutManager(this)
        binding.rvItensPedido.adapter = adapter
        iniciarListener()
    }

    fun iniciarListener() {
        val ctrlItens = PedidoController()
        listener =  ctrlItens.buscarPedido().addSnapshotListener {
                querySnapshot, erro ->

            if (erro != null) {
                erro.printStackTrace()
                return@addSnapshotListener
            }

            if (querySnapshot != null) {
                val novoPedido = mutableListOf<Pedido>()

                for (document in querySnapshot) {
                    try {
                        val usuarioNome = document.getString("usuarioNome") ?: "Usuário desconhecido"
                        val itensList = document.get("itens") as? List<Map<String, Any>> ?: emptyList()

                        val itens = itensList.mapNotNull { itemMap ->
                            try {
                                ItensCardapio(
                                    id = itemMap["id"] as? String,
                                    uid = itemMap["uid"] as? String,
                                    imagem = itemMap["imagem"] as? String,
                                    nome = itemMap["nome"] as? String ?: "Sem Nome",
                                    descricao = itemMap["descricao"] as? String,
                                    preco = itemMap["preco"] as? String
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                null
                            }
                        }.toMutableList()

                        novoPedido.add(Pedido(usuarioNome, itens))

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                adapter.atualizarLista(novoPedido)

                atualizarTotalPrice(novoPedido)
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        listener?.remove()
    }

    private fun atualizarTotalPrice(pedidos: List<Pedido>) {
        var totalPreco = 0.0

        pedidos.forEach { pedido ->
            pedido.itens.forEach { item ->
                totalPreco += item.preco?.toDoubleOrNull() ?: 0.0
            }
        }

        binding.tvTotalPrice.text = "RS %.2f".format(totalPreco)
    }
}