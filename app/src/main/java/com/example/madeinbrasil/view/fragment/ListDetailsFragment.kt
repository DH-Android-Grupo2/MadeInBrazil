package com.example.madeinbrasil.view.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentListDetailsBinding
import com.example.madeinbrasil.databinding.FragmentListsBinding
import com.example.madeinbrasil.view.activity.testeActivity
import com.example.madeinbrasil.view.classes.Lista

class ListDetailsFragment : Fragment() {
    private lateinit var binding: FragmentListDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)



        val listas = mutableListOf<Lista>()

        binding.rvListDetails.apply { layoutManager = GridLayoutManager(view.context,2)
        adapter = ListAdapter(listas) { position ->
            val intent = Intent(view.context, testeActivity::class.java)
            intent.putExtra(ListsFragment.KEY_INTENT_LISTA, listas[position])
            startActivity(intent)

        }
        }
        }
    }



