package com.example.madeinbrasil.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.databinding.FragmentListsBinding
import com.example.madeinbrasil.view.activity.ListDetailActivity
import com.example.madeinbrasil.view.activity.UserActivity
import com.example.madeinbrasil.view.adapter.ListAdapter
import com.example.madeinbrasil.view.classes.Item
import com.example.madeinbrasil.view.classes.Lista


class ListsFragment : Fragment() {
   private lateinit var binding: FragmentListsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivProfileLists?.setOnClickListener {
            this.context?.let { it1 -> startUserActivity(it1) }
        }
        val item = Item("filme do babu", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")
        val item2 = Item("filme do santoro", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")
        val item3 = Item("filme do babu", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")



        val lista1 = Lista("Filmes do Babu", "criada por Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista2 = Lista("Filmes do Santoro", "criada por Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista3 = Lista("Filmes do Babu", "criada por Eduardo Misina","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista5 = Lista("Filmes do Babu", "criada por César","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista6 = Lista("Filmes do Santoro", "Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista7 = Lista("Filmes do Babu", "Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")

        lista1.itens.addAll(listOf(item,item2,item3))
        lista2.itens.addAll(listOf(item,item2,item3))
        lista3.itens.addAll(listOf(item,item2,item3))


        val listas = mutableListOf<Lista>()

        listas.addAll(listOf(lista1,lista2,lista3,lista5,lista6,lista7))

        binding.rvListas.apply { layoutManager = LinearLayoutManager(view.context)
            adapter = ListAdapter(listas) { position ->
                val intent = Intent(view.context, ListDetailActivity::class.java)
                intent.putExtra(KEY_INTENT_LISTA, listas[position])
                startActivity(intent)
            }
        }



    }
    fun startUserActivity(context: Context) {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }
    companion object {
        const val KEY_INTENT_LISTA = "lista"
    }
}




