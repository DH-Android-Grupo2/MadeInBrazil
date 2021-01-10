package com.example.madeinbrasil.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentMyListsBinding
import com.example.madeinbrasil.view.activity.CreateListActivity
import com.example.madeinbrasil.view.activity.UserActivity
import com.example.madeinbrasil.view.adapter.MainAdapterFilm

class MyListsFragment : Fragment() {
    private lateinit var binding: FragmentMyListsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btNewList?.setOnClickListener {
            this.context?.let { it1 -> startCreateListActivity(it1) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    private fun startCreateListActivity(context: Context) {
        val intent = Intent(context, CreateListActivity::class.java)
        startActivity(intent)
    }
}