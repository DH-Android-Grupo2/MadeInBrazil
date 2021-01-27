package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityCustomListDetailsBinding
import com.example.madeinbrasil.model.customLists.ListMediaItem
import com.example.madeinbrasil.view.adapter.ListDetailsAdapter
import com.example.madeinbrasil.view.fragment.MyListsFragment.Companion.LIST_ID
import com.example.madeinbrasil.viewModel.CustomListViewModel
import kotlinx.coroutines.launch

class CustomListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomListDetailsBinding
    private lateinit var customListViewMovel: CustomListViewModel
    private var actionMode: ActionMode? = null
    private var selectedMovies: List<Long>? = null
    private var listID: Long? = null
    private val listDetailsAdapter by lazy {
        ListDetailsAdapter(mutableListOf()) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customListViewMovel = ViewModelProvider(this).get(CustomListViewModel::class.java)
        intent?.getLongExtra(LIST_ID, 1)?.let { getListUni(it) }

        listDetailsAdapter.onMediaClick = {
            enableActionMode(it)
        }

        listDetailsAdapter.onMediaLongClick = {
            enableActionMode(it)
        }
    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null)
            actionMode = startSupportActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    if (item?.itemId == R.id.action_delete) {
                       deleteItemsDb(listDetailsAdapter.selectedItems)
                        listDetailsAdapter.deleteMedia()
                        mode?.finish()
                        return true
                    }
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    listDetailsAdapter.let {
                        it.selectedPositions.clear()
                        it.selectedItems.clear()
                        it.notifyDataSetChanged()
                    }

                    actionMode = null
                }

            })
        listDetailsAdapter.tooglePosition(position)
        val size = listDetailsAdapter.selectedItems.size
        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = size.toString()
            actionMode?.invalidate()
        }
    }


    private fun getListUni(listId: Long) {
        customListViewMovel.getListUni(listId)

        customListViewMovel.uniList.observe(this) { list ->
            listID = list.list.listId
            with(binding) {
                tvListName.text = list.list.name
                list.list.description
                val desc = list.list.description
                if (desc == "")
                    tvListDescription.text = getString(R.string.sem_descricao_label)
                else
                    tvListDescription.text = desc

                val media = list.media
                Log.i("listMedia", media.toString())
                if (media.isNotEmpty())
                rvListItems.apply {
                    layoutManager = GridLayoutManager(this@CustomListDetailsActivity, 2)
                    listDetailsAdapter.list = media as MutableList<ListMediaItem>
                    adapter = listDetailsAdapter
                    }
                    else
                        tvEmptyMessage.visibility = View.VISIBLE
            }

            customListViewMovel.getCustomListMovieIds(list.list.listId)

            customListViewMovel.customListMovieIds.observe(this) {
                selectedMovies = it
                Log.i("MoviesId", it.toString())
            }
        }

    }

    private fun deleteItemsDb(selectedItems: MutableList<Long>) {
        var selectedSeriesId = mutableListOf<Long>()
        var selectedMoviesId = mutableListOf<Long>()

        selectedMovies?.let { listMoviesId ->
            if (listMoviesId.isNotEmpty()) {
                selectedItems.forEach {
                    if (!listMoviesId.contains(it))
                        selectedSeriesId.add(it)
                    else
                        selectedMoviesId.add(it)
                }
            } else {
                selectedSeriesId = selectedItems
            }
        }

            if (selectedSeriesId.isNotEmpty())
                listID?.let { customListViewMovel.deleteSeriesFromList(it, selectedSeriesId) }

            if (selectedMoviesId.isNotEmpty())
                listID?.let { customListViewMovel.deleteMoviesFromList(it, selectedMoviesId) }

    }
}