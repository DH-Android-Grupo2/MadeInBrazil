package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dh.madeinbrasil.databinding.UserListsRecyclerviewBinding
import com.dh.madeinbrasil.model.customLists.firebase.CustomList

class ListsAdapter(
    private val listCustomList: List<CustomList>,
    private val onClickFav: (CustomList) -> Unit
): RecyclerView.Adapter<ListsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserListsRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCustomList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCustomList[position], onClickFav)
    }

    class ViewHolder(
        private val binding: UserListsRecyclerviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(customList: CustomList, onClick: (CustomList) -> Unit) = with(binding) {

        }
    }
}