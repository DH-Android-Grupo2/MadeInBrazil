package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.databinding.CustomListItemBinding

import com.example.madeinbrasil.databinding.MainMyListRecyclerviewBinding
import com.example.madeinbrasil.model.customLists.CustomList

class ChooseListAdapter(private val list: List<CustomList>,
                        val onClick: (Long) -> Unit): RecyclerView.Adapter<ChooseListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(CustomListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: CustomListItemBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: CustomList, onClick: (Long) -> Unit) = with(binding) {
            tvcustomListName.text = list.name

            root.setOnClickListener {
                onClick(list.listId)
            }
        }

    }

}