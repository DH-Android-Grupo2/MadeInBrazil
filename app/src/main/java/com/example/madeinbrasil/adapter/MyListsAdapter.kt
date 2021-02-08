package com.example.madeinbrasil.adapter

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
import com.example.madeinbrasil.model.customLists.ListWithMediaUni


class MyListsAdapter(private val list: List<ListWithMedia>,
                     val onClick: (ListWithMedia) -> Unit): RecyclerView.Adapter<MyListsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MainMyListRecyclerviewBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: MainMyListRecyclerviewBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: ListWithMedia, onClick: (ListWithMedia) -> Unit) = with(binding) {
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
                        Glide.with(containerView).load(list.mediaList[i].backdropPath).into(imageView)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        containerView.addView(imageView)
                        }
                     } else {
                        for (i in 0 until 4) {
                            val imageView = ImageView(containerView.context)
                            val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
                            lp.weight = 1F
                            imageView.layoutParams = lp
                            Glide.with(containerView).load(list.mediaList[i].backdropPath).into(imageView)
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            containerView.addView(imageView)
                        }
            }

            tvListName.text = list.list.name

            root.setOnClickListener {
                onClick(list)
            }
        }

    }

}