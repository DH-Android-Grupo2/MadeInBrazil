package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityCustomListDetailsBinding
import com.example.madeinbrasil.databinding.ActivityPublicCustomListDetailBinding
import com.example.madeinbrasil.model.customLists.ListWithMedia
import com.example.madeinbrasil.model.customLists.firebase.Media
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.view.adapter.ListDetailsAdapter
import com.example.madeinbrasil.view.adapter.PublicListDetailsAdapter
import com.example.madeinbrasil.viewModel.CustomListViewModel

class PublicCustomListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPublicCustomListDetailBinding
    private lateinit var customListViewMovel: CustomListViewModel
    private var actionMode: ActionMode? = null
    private var selectedMovies: List<String>? = null
    private var selectedSeries: List<String>? = null
    private lateinit var listID: String
    private val listDetailsAdapter by lazy {
        PublicListDetailsAdapter() {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicCustomListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customListViewMovel = ViewModelProvider(this).get(CustomListViewModel::class.java)
        intent?.getParcelableExtra<ListWithMedia>(Constants.CustomLists.LIST)?.let {
            loadListInfo(it)
            listID = it.list.id
            selectedMovies = it.list.movies
            selectedSeries = it.list.series
        }

    }

    private fun loadListInfo(list: ListWithMedia) = with(binding) {
        tvListName.text = list.list.name
        list.list.description
        val desc = list.list.description
        if (desc == "")
            tvListDescription.text = getString(R.string.sem_descricao_label)
        else
            tvListDescription.text = desc

        val media = list.mediaList
        if (media.isNotEmpty())
            rvListItems.apply {
                layoutManager = GridLayoutManager(this@PublicCustomListDetailActivity, 2)
                listDetailsAdapter.list = media as MutableList<Media>
                adapter = listDetailsAdapter
            }
        else
            tvEmptyMessage.visibility = View.VISIBLE
    }


}
