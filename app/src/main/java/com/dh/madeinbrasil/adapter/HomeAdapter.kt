package com.dh.madeinbrasil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.databinding.*
import com.dh.madeinbrasil.model.upcoming.Result
import kotlinx.android.synthetic.main.filmsseries_popup.*
import kotlinx.android.synthetic.main.main_cards_menu.view.*


class HomeAdapter(
        private val onMovieClicked: (Result?,ImageView?) -> Unit,
        private val onMovieLongClicked: (Result?) -> Unit
) : PagedListAdapter<Result, HomeAdapter.ViewHolder>(Result.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainCardsMenuBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClicked, onMovieLongClicked)
    }

    class ViewHolder(
            private val binding: MainCardsMenuBinding
    ): RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(
            movie: Result?,
            onMovieClicked: (Result?, ImageView?) -> Unit,
            onMovieLongClicked: (Result?) -> Unit
        ) = with(binding) {
            Glide.with(itemView.context)
                .load(movie?.posterPath)
                .placeholder(R.drawable.logo_made_in_brasil)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(cvImageCardMenu)
            tvNameRecyclerViewMenu.text = movie?.title

            itemView.setOnClickListener {
                onMovieClicked(movie, cvImageCardMenu)
            }

            itemView.setOnLongClickListener {
                onMovieLongClicked(movie)
                return@setOnLongClickListener true
            }

//            itemView.setOnLongClickListener {
//                val dialog = Dialog(it.context)
//                dialog.setContentView(R.layout.filmsseries_popup)
//                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//                Glide.with(it.context)
//                    .load(movie?.posterPath)
//                    .placeholder(R.drawable.logo_made_in_brasil)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(dialog.ivDialogPoster)
//
//                dialog.tvDialogName.text = movie?.title
//                dialog.cbShare.setOnClickListener {
//                    val image: Bitmap? = getBitmapFromView(binding.cvImageCardMenu)
//                    val sendIntent: Intent = Intent().apply {
//                        action = Intent.ACTION_SEND
//
//                        type = "text/plain"
//                        putExtra(Intent.EXTRA_TEXT, "Filme: ${movie?.title} by MadeInBrasil")
//
//                        type = "image/*"
//                        putExtra(Intent.EXTRA_STREAM, image?.let { it1 ->
//                            getImageUri(
//                                    it.context,
//                                    it1
//                            )
//                        })
//
//
//                        putExtra(
//                                Intent.EXTRA_TITLE,
//                                "Filme: ${movie?.title} \nShared by MadeInBrasil"
//                        )
//                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                    }
//                    val shareIntent = Intent.createChooser(sendIntent, "Compartilhamento de Filmes")
//                    ContextCompat.startActivity(it.context, shareIntent, null)
//
//                }
//                dialog.show()
//
//                return@setOnLongClickListener true
//            }
//        }
//
//        fun getBitmapFromView(view: ImageView): Bitmap? {
//            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(bitmap)
//            view.draw(canvas)
//            return bitmap
//        }
//
//        fun getImageUri(inContext: Context, inImage: Bitmap):Uri?{
//            val bytes = ByteArrayOutputStream()
//            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//            val path = MediaStore.Images.Media.insertImage(
//                    inContext.contentResolver,
//                    inImage,
//                    "${binding.tvNameRecyclerViewMenu.text}",
//                    "Imagem gerado pelo MadeInBrasil"
//            )
//            val f: File = File(path)
//            return Uri.fromFile(f)
//
//        }

        }
    }
}