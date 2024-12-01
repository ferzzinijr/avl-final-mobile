package com.example.avl_final_mobile.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.controller.CardapioAdapter
import com.example.avl_final_mobile.controller.CardapioController
import com.example.avl_final_mobile.databinding.ActivityCardapioBinding
import com.example.avl_final_mobile.`interface`.OnDetalhesClickListener
import com.example.avl_final_mobile.model.ItensCardapio
import com.google.firebase.firestore.ListenerRegistration

class CardapioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardapioBinding
    private lateinit var ctrl: AutenticacaoController

    private lateinit var adapter: CardapioAdapter
    private var lista = mutableListOf<ItensCardapio>()
    private var listener: ListenerRegistration? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CardapioAdapter(lista, object: OnDetalhesClickListener {
            override fun onDetalhesClick(item: ItensCardapio) {
                val it = Intent(this@CardapioActivity, ItemDetalhesActivity:: class.java)
                it.putExtra("item_id", item.id)
                it.putExtra("item_nome", item.nome)
                it.putExtra("item_descricao", item.descricao)
                it.putExtra("item_imagem", item.imagem)
                it.putExtra("item_preco", item.preco)
                startActivity(it)
            }
        })
        binding.rvBolos.layoutManager = LinearLayoutManager(this)
        binding.rvBolos.adapter = adapter
        iniciarListener()


        binding.btnLogout.setOnClickListener {
            ctrl.logout()
        }

        binding.btnPedido.setOnClickListener {
            val it = Intent(this, PedidoActivity:: class.java)
            startActivity(it)
        }
    }

    fun iniciarListener() {
        val ctrlItens = CardapioController()
        listener =  ctrlItens.listarItens().addSnapshotListener {
                querySnapshot, erro ->

            if (erro != null) {
                return@addSnapshotListener
            }

            if (querySnapshot != null) {
                val novosItens = mutableListOf<ItensCardapio>()

                for (document in querySnapshot) {
                    novosItens.add(
                        ItensCardapio(
                            document.id,
                            document.getString("uid"),
                            document.getString("imagem"),
                            document.getString("nome"),
                            document.getString("descricao"),
                            document.getString("preco")
                        )
                    )
                }
                adapter.atualizarLista(novosItens)
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        listener?.remove()
    }
}