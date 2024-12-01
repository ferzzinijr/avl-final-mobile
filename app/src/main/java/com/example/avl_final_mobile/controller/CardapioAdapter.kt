package com.example.avl_final_mobile.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.avl_final_mobile.databinding.ItemBoloBinding
import com.example.avl_final_mobile.`interface`.OnDetalhesClickListener
import com.example.avl_final_mobile.model.ItensCardapio

class CardapioAdapter (private val lista: MutableList<ItensCardapio>, private val listener: OnDetalhesClickListener) :
    RecyclerView.Adapter<CardapioAdapter.CardapioViewHolder>(){

    class CardapioViewHolder(val binding: ItemBoloBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardapioViewHolder {
        val binding = ItemBoloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardapioViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: CardapioViewHolder, position: Int) {
        if (getItemCount() > 0) {
            val i = lista[position]

            val sourceId = holder.itemView.context.resources.getIdentifier(i.imagem,"drawable",holder.itemView.context.packageName)
            holder.binding.imgBolo.setImageResource(sourceId)
            holder.binding.txtNomeBolo.text = i.nome
            holder.binding.txtPrecoBolo.text = buildString {
                append("RS ")
                append(i.preco)
            }

            holder.binding.icDetalhes.setOnClickListener {
                listener.onDetalhesClick(i)
            }
        }
    }

    fun atualizarLista(novosItens: List<ItensCardapio>){
        lista.clear()
        lista.addAll(novosItens)
        notifyDataSetChanged()
    }
}