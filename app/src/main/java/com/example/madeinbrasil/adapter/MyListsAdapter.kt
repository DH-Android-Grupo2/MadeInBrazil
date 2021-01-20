package com.example.madeinbrasil.adapter

import android.content.Context
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
import com.example.madeinbrasil.model.customLists.ListWithMediaUni


class MyListsAdapter(private val list: List<ListWithMediaUni>): RecyclerView.Adapter<MyListsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(MainMyListRecyclerviewBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: MainMyListRecyclerviewBinding) : RecyclerView.ViewHolder(
            binding.root
    ) {
        fun bind(list: ListWithMediaUni) = with(binding) {
            val mediaSize = list.media.size
            if (mediaSize == 0) {
                val textView = TextView(containerView.context)
                textView.text = "Vazia"
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
//
                        imageView.layoutParams = LinearLayout.LayoutParams(convertDp(102/mediaSize, containerView.context), LinearLayout.LayoutParams.MATCH_PARENT)
                        Glide.with(containerView).load(list.media[i].backdropPath).into(imageView)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        containerView.addView(imageView)
                        }
                     } else {
                        for (i in 0 until 4) {
                            val imageView = ImageView(containerView.context)

                            imageView.layoutParams = LinearLayout.LayoutParams(convertDp(26, containerView.context), LinearLayout.LayoutParams.MATCH_PARENT)
                            Glide.with(containerView).load(list.media[i].backdropPath).into(imageView)
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            containerView.addView(imageView)
                        }
            }

            tvListName.text = list.list.name
        }

        private fun convertDp(num: Int, context: Context): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (num * scale + 0.5f).toInt()
        }

    }

}