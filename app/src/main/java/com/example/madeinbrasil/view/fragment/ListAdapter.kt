package com.example.madeinbrasil.view.fragment



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.view.classes.Lista
import kotlinx.android.synthetic.main.rvprojeto_listitem.view.*
import kotlin.collections.List


class ListAdapter(
    private val itensList: List<Lista>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        Log.i("RecyclerView", "onCreateViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvprojeto_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        Log.i("RecyclerView", "onBindViewHolder - $position")
        holder.bind(itensList[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return itensList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(lista: Lista, onItemClicked: (Int) -> Unit) = with(itemView) {

            Glide.with(itemView.context).load(lista.imagem).into(ivItem)




            tvItemTitle.text = lista.nome
            tvItemCriador.text = lista.criador


            listaContainer.setOnClickListener {
                onItemClicked(this@ViewHolder.adapterPosition)
            }
        }
    }
}