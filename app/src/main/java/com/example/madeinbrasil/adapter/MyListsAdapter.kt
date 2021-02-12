package com.example.madeinbrasil.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.MainMyListRecyclerviewBinding
import com.example.madeinbrasil.model.customLists.ListWithMedia


class MyListsAdapter(val onClick: (ListWithMedia) -> Unit): RecyclerView.Adapter<MyListsAdapter.ViewHolder>() {

    lateinit var list: MutableList<ListWithMedia>
    var onItemLongClick: ((Int) -> Unit)? = null
    var onItemClick: ((Int) -> Unit)? = null
    val selectedPositions = SparseBooleanArray()
    var selectedItems: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MainMyListRecyclerviewBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{
            if (selectedItems.size > 0)
                onItemClick?.invoke(position)
            else
                onClick(list[position])
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = list.size

    fun tooglePosition(position: Int) {
        if(selectedPositions[position, false]) {
            selectedPositions.delete(position)
            selectedItems.remove(list[position].list.id)
        } else {
            selectedPositions.put(position, true)
            selectedItems.add(list[position].list.id)
        }

        notifyItemChanged(position)
    }

    fun deleteLists() {
        list.removeAll(
            list.filter {
                selectedItems.contains(it.list.id)
            }
        )

        selectedItems.clear()
        selectedPositions.clear()
        notifyDataSetChanged()
    }

    fun getItem(): ListWithMedia {
        return list.filter {
            it.list.id == selectedItems[0]
        }.first()
    }

    inner class ViewHolder(val binding: MainMyListRecyclerviewBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: ListWithMedia) = with(binding) {
            containerView.removeAllViews()
            val mediaSize = list.mediaList.size
            if (mediaSize == 0) {
                val textView = TextView(containerView.context)
                textView.text = containerView.context.getString(R.string.label_vazia)
                textView.textSize = 20F
                textView.setTextColor(
                        ResourcesCompat.getColor(
                                containerView.context.resources,
                                R.color.colorAccent,
                                null
                        )
                )
                containerView.addView(textView)
            } else if (mediaSize < 4) {
                    for (i in 0 until mediaSize) {
                        val imageView = ImageView(containerView.context)
                        val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
                        lp.weight = 1F
                        imageView.layoutParams = lp
                        Glide.with(containerView).load(list.mediaList[i].posterPath ?: list.mediaList[i].backdropPath).into(imageView)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        containerView.addView(imageView)
                        }
                     } else {
                        for (i in 0 until 4) {
                            val imageView = ImageView(containerView.context)
                            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
                            lp.weight = 1F
                            imageView.layoutParams = lp
                            Glide.with(containerView).load(list.mediaList[i].backdropPath ?: list.mediaList[i].backdropPath).into(imageView)
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            containerView.addView(imageView)
                        }
            }

            tvListName.text = list.list.name

            if(selectedItems.contains(list.list.id)) {
                root.setBackgroundColor(ResourcesCompat.getColor(root.context.resources, R.color.colorAccent, null))
                tvListName.setTextColor(ResourcesCompat.getColor(root.context.resources, R.color.colorPrimary, null))
            } else {
                root.setBackgroundColor(ResourcesCompat.getColor(root.context.resources, R.color.colorPrimary, null))
                tvListName.setTextColor(ResourcesCompat.getColor(root.context.resources, R.color.colorAccent, null))
            }

        }

    }

}