package com.example.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.view.classes.Item
import com.example.madeinbrasil.view.classes.Lista
import com.example.madeinbrasil.view.fragment.ListAdapter
import com.example.madeinbrasil.view.fragment.ListDetailsFragment

class testeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_lists)

        val item = Item("filme do babu", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")
        val item2 = Item("filme do santoro", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")
        val item3 = Item("filme do babu", "https://cdn-istoe-ssl.akamaized.net/wp-content/uploads/sites/14/2020/04/babu-santana.jpg")



        val lista1 = Lista("Filmes do kkkkkk", "criada por Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista2 = Lista("Filmes do kkkkkk", "criada por Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista3 = Lista("Filmes do Bkkkkkk", "criada por Eduardo Misina","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista5 = Lista("Filmes do Babu", "criada por César","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista6 = Lista("Filmes do Santoro", "Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")
        val lista7 = Lista("Filmes do Babu", "Vinicius Trapia","https://br.bolavip.com/__export/1587138813245/sites/bolavip/img/2020/04/17/babu_4_crop1587138812761.jpg_1693159006.jpg")


        lista1.itens.addAll(listOf(item,item2,item3))
        lista2.itens.addAll(listOf(item,item2,item3))
        lista3.itens.addAll(listOf(item,item2,item3))


        val listas = mutableListOf<Lista>()

        listas.addAll(listOf(lista1,lista2,lista3,lista5,lista6,lista7))



        findViewById<RecyclerView>(R.id.rvListas).apply {
            layoutManager = LinearLayoutManager(this@testeActivity)
            adapter = ListAdapter(listas) { position ->
                val intent = Intent(this@testeActivity, ListDetailsFragment::class.java)
                intent.putExtra(KEY_INTENT_LISTA, listas[position])
                startActivity(intent)
            }
        }

    }

    companion object {
        const val KEY_INTENT_LISTA = "lista"
    }
}



