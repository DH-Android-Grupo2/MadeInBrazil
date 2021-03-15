package com.dh.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.database.entities.User
import com.dh.madeinbrasil.database.entities.genre.GenreFirebase
import com.dh.madeinbrasil.database.entities.midia.MidiaFirebase
import com.dh.madeinbrasil.databinding.ActivityMenuBinding
import com.dh.madeinbrasil.model.gender.GenreSelected
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.TUTORIAL
import com.dh.madeinbrasil.view.fragment.FilmsFragment
import com.dh.madeinbrasil.view.fragment.HomeFragment
import com.dh.madeinbrasil.view.fragment.ListsFragment
import com.dh.madeinbrasil.view.fragment.SeriesFragment
import com.dh.madeinbrasil.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModel: MenuViewModel
    private var tutorial = 1
    var  genreList: GenreSelected? = null

    companion object {
        lateinit var USER: User
        var MIDIA: MutableList<MidiaFirebase> = mutableListOf()
        lateinit var GENRE: MutableList<GenreFirebase>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tutorial = intent.getIntExtra(TUTORIAL, 1)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        startObjects()

        intent?.let {
            genreList = it.getParcelableExtra<GenreSelected>("genreList")
        }
    }

    private fun startObjects() {
        viewModel.getUser()
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

            if(tutorial == 0) {
                initFragmentsTutorial(FilmsFragment())
                binding.bottomNavigation.selectedItemId = R.id.buttonFilms
            }else {
                initFragmentsHome(HomeFragment(), genreList, USER.tutorial)
            }

            binding.bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.buttonHome -> {
                        initFragmentsHome(HomeFragment(), genreList, USER.tutorial)
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
        viewModel.genre.observe(this) {
            GENRE = it
        }
    }

    private fun initFragments(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

    private fun initFragmentsTutorial(fragment: Fragment) {
        val fragmentStart = supportFragmentManager.beginTransaction()
        val bundle = Bundle()

        bundle.putInt(TUTORIAL, 0)
        fragment.arguments = bundle
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

    private fun initFragmentsHome(fragment: Fragment, genreSelected: GenreSelected?, tutorial: Int) {
        val bundle = Bundle()
        bundle.putParcelable("Selected", (genreSelected))
        bundle.putInt(TUTORIAL, tutorial)
        fragment.arguments = bundle

        val fragmentStart = supportFragmentManager.beginTransaction()
        fragmentStart.replace(R.id.flContainerMenu, fragment)
        fragmentStart.commit()
    }

}