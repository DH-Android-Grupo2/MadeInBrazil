package com.example.madeinbrasil.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.databinding.FragmentListsBinding
import com.example.madeinbrasil.model.customLists.ListWithMedia
import com.example.madeinbrasil.utils.Constants.CustomLists.LIST
import com.example.madeinbrasil.view.activity.CustomListDetailsActivity
import com.example.madeinbrasil.view.activity.ListDetailActivity
import com.example.madeinbrasil.view.activity.PublicCustomListDetailActivity
import com.example.madeinbrasil.view.activity.UserActivity
import com.example.madeinbrasil.view.adapter.ListAdapter
import com.example.madeinbrasil.view.classes.Item
import com.example.madeinbrasil.view.classes.Lista
import com.example.madeinbrasil.viewModel.CustomListViewModel
import kotlinx.android.synthetic.main.fragment_my_lists.*


class ListsFragment : Fragment() {

   private lateinit var binding: FragmentListsBinding
   private lateinit var customListViewMovel: CustomListViewModel

   private val listsAdapter by lazy {
        ListAdapter() { lista ->
            val intent = Intent(this@ListsFragment.context, PublicCustomListDetailActivity::class.java)
            intent.putExtra(LIST, lista)
            startActivity(intent)
        }
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivProfileLists.setOnClickListener {
            this.context?.let { it1 -> startUserActivity(it1) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customListViewMovel =  ViewModelProvider(this).get(CustomListViewModel::class.java)
    }

    fun startUserActivity(context: Context) {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.tvEmptyListMessage.visibility = View.GONE

        customListViewMovel.getListWithMedia(true)

        customListViewMovel.getListsSuccess.observe(this, { list ->

            pbGettingLists.visibility = View.GONE

            if(list.isEmpty()) {
                binding.tvEmptyListMessage.visibility = View.VISIBLE
            } else {
                binding.tvEmptyListMessage.visibility = View.GONE
                setupRecyclerView(list)
            }

        })

        customListViewMovel.listFailure.observe(this, { message ->

            pbGettingLists.visibility = View.GONE

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        })

    }

    private fun setupRecyclerView(list: List<ListWithMedia>) {
        binding.rvListas.apply {
            layoutManager = LinearLayoutManager(this@ListsFragment.context)
            listsAdapter.list = list
            adapter = listsAdapter
        }
    }

    companion object {
        const val KEY_INTENT_LISTA = "lista"
    }
}




