package com.example.avl_final_mobile.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avl_final_mobile.databinding.ItensPedidoBinding
import com.example.avl_final_mobile.`interface`.OnDetalhesClickListener
import com.example.avl_final_mobile.model.Pedido

class PedidoAdapter (private val pedido: MutableList<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(val binding: ItensPedidoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding = ItensPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pedido.size
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val i = pedido[position]

        if (i.itens.isNotEmpty()) {
            val firstItem = i.itens.first()

            val sourceId = if (!firstItem.imagem.isNullOrEmpty()) {
                holder.itemView.context.resources.getIdentifier(
                    firstItem.imagem,
                    "drawable",
                    holder.itemView.context.packageName
                )
            } else {
                holder.itemView.context.resources.getIdentifier(
                    "placeholder",
                    "drawable",
                    holder.itemView.context.packageName
                )
            }

            holder.binding.imgItem.setImageResource(sourceId)
            holder.binding.txtNomeItem.text = firstItem.nome
            holder.binding.txtPrecoItem.text = buildString {
                append("RS ")
                append(firstItem.preco)
            }
        } else {
            holder.binding.imgItem.setImageResource(
                holder.itemView.context.resources.getIdentifier(
                    "placeholder",
                    "drawable",
                    holder.itemView.context.packageName
                )
            )
            holder.binding.txtNomeItem.text = "Sem itens"
            holder.binding.txtPrecoItem.text = "RS 0,00"
        }
    }

    fun atualizarLista(novoPedido: List<Pedido>) {
        pedido.clear()
        pedido.addAll(novoPedido)
        notifyDataSetChanged()
    }
}