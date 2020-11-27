package com.example.madeinbrasil.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.view.classes.Lista
import kotlinx.android.synthetic.main.fragment_list_details.view.*
import kotlinx.android.synthetic.main.rvprojeto_listitem.view.*

//class ListDetailsAdapter (
//    private val itensList2: List<Lista>,
//    private val onItemClicked2: (Int) -> Unit
//) : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>()  {

//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailsAdapter.ViewHolder {
//        Log.i("RecyclerView", "onCreateViewHolder")
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.rvprojeto_listdetails_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ListDetailsAdapter.ViewHolder, position: Int) {
//        Log.i("RecyclerView", "onBindViewHolder - $position")
//        holder.bind(itensList2[position], onItemClicked2)
//    }
//
//    override fun getItemCount(): Int {
//        return itensList2.size
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(lista2: Lista, onItemClicked: (Int) -> Unit) = with(itemView) {
//
//            Glide.with(itemView.context).load(lista2.imagem).into(ivListDetailItem)
//
//
//
//
//            tvListTeste.text = lista2.nome
//
//
//
//            testeContainer.setOnClickListener {
//                onItemClicked(this@ViewHolder.adapterPosition)
//            }
//        }
//    }
//}
