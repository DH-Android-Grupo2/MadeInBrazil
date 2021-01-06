package com.example.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityMenuBinding
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.view.fragment.FilmsFragment
import com.example.madeinbrasil.view.fragment.HomeFragment
import com.example.madeinbrasil.view.fragment.ListsFragment
import com.example.madeinbrasil.view.fragment.SeriesFragment
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    var  genreList: GenreSelected? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


      intent?.let {
       genreList = it.getParcelableExtra<GenreSelected>("genreList")
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

        supportActionBar?.hide()
        tutorialImplementation()
    }

    private fun tutorialImplementation() {
        TapTargetSequence(this).targets(
                TapTarget.forView(binding.bottomNavigation,
                        "Navegação",
                        "Aqui você poderá Navegar pelo Made In Brasil")
                        .cancelable(true)
                        .transparentTarget(true).targetRadius(100)
        ).listener(object: TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {}
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
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