package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.databinding.MainCardsBinding
import com.example.madeinbrasil.databinding.UserListsRecyclerviewBinding
import com.example.madeinbrasil.model.customLists.firebase.CustomList
import com.example.madeinbrasil.view.activity.MenuActivity

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