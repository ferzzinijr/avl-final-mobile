package com.example.avl_final_mobile.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.avl_final_mobile.controller.AutenticacaoController
import com.example.avl_final_mobile.controller.PedidoController
import com.example.avl_final_mobile.controller.UsuarioController
import com.example.avl_final_mobile.databinding.ActivityItemDetalhesBinding
import com.example.avl_final_mobile.model.ItensCardapio
import com.example.avl_final_mobile.model.Pedido

class ItemDetalhesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetalhesBinding
    private lateinit var ctrl: AutenticacaoController
    private lateinit var ctrlUsuario: UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getStringExtra("item_id")
        val itemNome = intent.getStringExtra("item_nome")
        val itemDescricao = intent.getStringExtra("item_descricao")
        val itemImagem = intent.getStringExtra("item_imagem")
        val itemPreco = intent.getStringExtra("item_preco")

        binding.txtNomeItem.text = itemNome
        binding.txtDescricaoItem.text = itemDescricao
        binding.txtPrecoItem.text = "RS $itemPreco"

        val sourceId = resources.getIdentifier(itemImagem, "drawable", packageName)
        binding.imgItem.setImageResource(sourceId)

        binding.btnAddCarrinho.setOnClickListener {
            val i = ItensCardapio()

            i.id = itemId
            i.nome = itemNome
            i.descricao = itemDescricao
            i.preco = itemPreco

            val ctrlPedidos = PedidoController()
            val ctrlUsuario = UsuarioController()
            var nomeUsuario: String? = null

            ctrlUsuario.nomeUsuario { nome ->
                nomeUsuario = nome
            }

            ctrlPedidos.adicionarOuAtualizarPedido(nomeUsuario, i) {sucesso, erro ->
                if (sucesso)
                    Toast.makeText(this, "Item adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Erro: " + erro.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}