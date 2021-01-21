package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.MyListsAdapter
import com.example.madeinbrasil.databinding.ActivityCustomListDetailsBinding
import com.example.madeinbrasil.view.adapter.ListDetailsAdapter
import com.example.madeinbrasil.view.fragment.MyListsFragment.Companion.LIST_ID
import com.example.madeinbrasil.viewModel.CustomListViewModel

class CustomListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomListDetailsBinding
    private lateinit var customListViewMovel: CustomListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customListViewMovel = ViewModelProvider(this).get(CustomListViewModel::class.java)
        intent?.getLongExtra(LIST_ID, 1)?.let { getListUni(it) }
    }

    private fun getListUni(listId: Long) {
        customListViewMovel.getListUni(listId)

        customListViewMovel.uniList.observe(this) { list ->
            with(binding) {
                tvListName.text = list.list.name
                list.list.description
                val desc = list.list.description
                if (desc == "")
                    tvListDescription.text = getString(R.string.sem_descricao_label)
                else
                    tvListDescription.text = desc

                val media = list.media

                if (media.isNotEmpty())
                rvListItems.apply {
                    layoutManager = GridLayoutManager(this@CustomListDetailsActivity, 2)
                    adapter = ListDetailsAdapter(media) {

                    }
                } else
                    tvEmptyMessage.visibility = View.VISIBLE
            }
        }

    }
}