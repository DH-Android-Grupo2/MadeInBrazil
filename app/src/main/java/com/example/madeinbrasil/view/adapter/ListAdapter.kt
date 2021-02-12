package com.example.madeinbrasil.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.RvprojetoListitemBinding
import com.example.madeinbrasil.model.customLists.ListWithMedia


class ListAdapter(val onClick: (ListWithMedia) -> Unit): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    lateinit var list: List<ListWithMedia>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(RvprojetoListitemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(l: MutableList<ListWithMedia>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvprojetoListitemBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: ListWithMedia, onClick: (ListWithMedia) -> Unit) = with(binding) {
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

            val ownerText = "Criada por: ${list.list.ownerName}"
            tvListOwner.text = ownerText

            root.setOnClickListener {
                onClick(list)
            }
        }

    }

}