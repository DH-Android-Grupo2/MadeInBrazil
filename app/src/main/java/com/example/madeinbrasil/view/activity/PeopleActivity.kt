package com.example.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.FilmsAdapter
import com.example.madeinbrasil.adapter.FilmsSeriesFromUserAdapter
import com.example.madeinbrasil.adapter.MovieCreditsAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.databinding.ActivityPeopleBinding
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.viewModel.MovieCreditsViewModel
import com.example.madeinbrasil.viewmodel.PersonViewModel

class PeopleActivity : AppCompatActivity() {
    private var people: Cast? = null
    private lateinit var binding: ActivityPeopleBinding
    private lateinit var viewModelPeople: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        people = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_ACTOR_KEY)

        viewModelPeople = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewModelPeople.getPerson(people?.id)

        binding.apply {
            tvNameFilmsSeries.text = people?.name

            Glide.with(binding.root.context)
                .load(people?.profile_path)
                .into(ivBannerFilmsSeries)
            Glide.with(binding.root.context)
                    .load(people?.profile_path)
                    .into(ivBackDropFilmSeries)

            viewModelPeople.personSucess.observe(this@PeopleActivity, {
                it?.let { pessoa ->
                    tvDescriptionTextFilmsSeries.text = pessoa.biography
                    Log.i("CLICOU","${pessoa.movie_credits}")
                    binding.rvCardsListFilmes.apply {
                        layoutManager = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = pessoa.movie_credits?.let { it1 ->
                            FilmsSeriesFromUserAdapter(it1.cast){
                                Log.i("CLICOU","CLICOU")
                            }
                        }
                    }

                }
            })

        }




    }
}