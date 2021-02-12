package com.example.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.CustomListItemBinding
import com.example.madeinbrasil.model.customLists.firebase.CustomList

class ChooseListAdapter(): RecyclerView.Adapter<ChooseListAdapter.ViewHolder>() {

    lateinit var list: List<CustomList>
    var onclick: ((CustomList) -> Unit)? = null
    var selectedPosition: Int = -1
    var selectedItem: CustomList = CustomList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(CustomListItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            if(selectedPosition != position) {
                onclick?.invoke(list[position])
                tooglePosition(position)
            }
        }
    }

    private fun tooglePosition(position: Int) {
        val oldPosition = selectedPosition
        selectedItem = list[position]
        selectedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: CustomListItemBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: CustomList) = with(binding) {
            tvcustomListName.text = list.name.trim()

            if(selectedItem.id == list.id) {
                listIcon.setImageResource(R.drawable.list_icon2)
                root.setBackgroundColor(ResourcesCompat.getColor(root.context.resources, R.color.colorAccent, null))
                tvcustomListName.setTextColor(ResourcesCompat.getColor(root.context.resources, R.color.colorPrimary, null))
            } else {
                listIcon.setImageResource(R.drawable.list_icon)
                root.setBackgroundColor(ResourcesCompat.getColor(root.context.resources, R.color.colorPrimary, null))
                tvcustomListName.setTextColor(ResourcesCompat.getColor(root.context.resources, R.color.colorAccent, null))
            }

        }

    }

}