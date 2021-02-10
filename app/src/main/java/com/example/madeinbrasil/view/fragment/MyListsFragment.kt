package com.example.madeinbrasil.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.MyListsAdapter
import com.example.madeinbrasil.databinding.FragmentMyListsBinding
import com.example.madeinbrasil.view.activity.CreateListActivity
import com.example.madeinbrasil.view.activity.CustomListDetailsActivity
import com.example.madeinbrasil.viewModel.CustomListViewModel
import com.google.android.material.snackbar.Snackbar


class MyListsFragment : Fragment() {
    private lateinit var binding: FragmentMyListsBinding

    private lateinit var customListViewMovel: CustomListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customListViewMovel =  ViewModelProvider(this).get(CustomListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNewList.setOnClickListener {
            this.context?.let { it1 -> startCreateListActivity(it1) }
        }
    }

    override fun onResume() {
        super.onResume()

        customListViewMovel.getListsUni()

        customListViewMovel.uniLists.observe(viewLifecycleOwner) {

            binding.rvMyLists.apply {
                layoutManager = LinearLayoutManager(this@MyListsFragment.context)
                adapter = MyListsAdapter(it) { id ->
                    val intent = Intent(this@MyListsFragment.context, CustomListDetailsActivity::class.java)
                    intent.putExtra(LIST_ID, id)
                    startActivity(intent)
                }
            }
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    private fun startCreateListActivity(context: Context) {
        val intent = Intent(context, CreateListActivity::class.java)
        startActivityForResult(intent, CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CODE) {
            makeToast()
        }
    }

    private fun makeToast() {
        Snackbar.make(binding.root, R.string.created_list_success, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.colorAccent, null))
                .show()
    }

    companion object {
        const val CODE = 888
        const val LIST_ID = "list_id"
    }
}