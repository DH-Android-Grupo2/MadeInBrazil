package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.madeinbrasil.R
import com.example.madeinbrasil.database.entities.User
import com.example.madeinbrasil.database.entities.cast.CastFirebase
import com.example.madeinbrasil.database.entities.genre.GenreFirebase
import com.example.madeinbrasil.database.entities.midia.MidiaFirebase
import com.example.madeinbrasil.database.entities.season.SeasonFirebase
import com.example.madeinbrasil.databinding.ActivityMenuBinding
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.HomeFragment
import com.example.madeinbrasil.view.fragment.ListsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment
import com.example.madeinbrasil.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MenuViewModel
    var  genreList: GenreSelected? = null

    companion object {
        lateinit var USER: User
        var MIDIA: MutableList<MidiaFirebase> = mutableListOf()
//        lateinit var CAST: MutableList<CastFirebase>
//        lateinit var SEASON: MutableList<SeasonFirebase>
        lateinit var GENRE: MutableList<GenreFirebase>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        startObjects()

        intent?.let {
            genreList = it.getParcelableExtra<GenreSelected>("genreList")
        }
    }

    private fun startObjects() {
        viewModel.getUser()
//        viewModel.getCast()
//        viewModel.getSeason()
        viewModel.getGenre()
        viewModel.user.observe(this) {doc ->
            USER = doc
            val listComplete : MutableList<Int> = USER.favorites

            USER.watched.forEach {
                if(!listComplete.contains(it)) {
                    listComplete.add(it)
                }
            }

            listComplete.forEach {
                viewModel.getMidia(it)
            }

            initFragmentsHome(HomeFragment(), genreList)
            binding.bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.buttonHome -> {
                        initFragmentsHome(HomeFragment(),genreList)
                        true
                    }
                    R.id.buttonFilms -> {
                        initFragments(FilmsFragment())
                        true
                    }
                    R.id.buttonLists -> {
                        initFragments(ListsFragment())
                        true
                    }
                    R.id.buttonSeries -> {
                        initFragments(SeriesFragment())
                        true
                    }
                    else -> false
                }
            }
        }
        viewModel.midia.observe(this) {midia ->
            midia?.let {
                it.forEach {doc ->
                    val obj = doc.toObject(MidiaFirebase::class.java)
                    obj?.let {
                        if(!MIDIA.contains(it)){
                            MIDIA.add(it)
                        }
                    }
                }
            }
        }
//        viewModel.cast.observe(this) {
//            CAST = it
//        }
//        viewModel.season.observe(this) {
//            SEASON = it
//        }
        viewModel.genre.observe(this) {
            GENRE = it
        }
    }

    private fun initFragments(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

      private fun initFragmentsHome(fragment: Fragment, genreSelected: GenreSelected?) {
       val bundle = Bundle()
       bundle.putParcelable("Selected", (genreSelected))
       fragment.arguments = bundle

       val fragmentStart = supportFragmentManager.beginTransaction()
       fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

}