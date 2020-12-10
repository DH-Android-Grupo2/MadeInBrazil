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
import com.example.madeinbrasil.adapter.CastDetailsAdapter
import com.example.madeinbrasil.adapter.FilmsAdapter
import com.example.madeinbrasil.adapter.FilmsSeriesFromUserAdapter
import com.example.madeinbrasil.adapter.MovieCreditsAdapter
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.databinding.ActivityPeopleBinding
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.extensions.getFullImagePath
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_ACTOR_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.example.madeinbrasil.viewModel.MovieCreditsViewModel
import com.example.madeinbrasil.viewModel.PersonViewModel
import java.text.SimpleDateFormat
import java.util.*

class PeopleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPeopleBinding
    private var people: Cast? = null
    private lateinit var viewModelPeople: PersonViewModel
    private var position: Int = 0
    private val date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val dateYear =  dateFormat.format(date)

        people = intent.getParcelableExtra(BASE_ACTOR_KEY)
        position = intent.getIntExtra(VALUE, 0)

        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }

        viewModelPeople = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewModelPeople.getPerson(people?.id)

        binding.apply {
            tvNameFilmsSeries.text = people?.name

            Glide.with(binding.root.context)
                    .load(people?.profilePath)
                    .into(ivBannerFilmsSeries)
            Glide.with(binding.root.context)
                    .load(people?.profilePath)
                    .into(ivBackDropFilmSeries)

            viewModelPeople.personSucess.observe(this@PeopleActivity) {
                it?.let { pessoa ->

                    pessoa.birthday?.let {ano ->
                        pessoa.birthday = "${(ano.toInt().let { it1 -> dateYear.toInt().minus(it1) })} anos"
                    }

                    tvDescriptionTextFilmsSeries.text = pessoa.biography
                    tvYearFilmsSeries.text = pessoa.birthday

                    Log.i("CLICOU","${pessoa.movie_credits}")
                    binding.rvCardsListFilmes.apply {
                        layoutManager = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                                adapter = pessoa.movie_credits?.let { it1 ->
                                    it1.cast?.let { it2 ->
                                        FilmsSeriesFromUserAdapter(it2){ result->
                                            val intent = Intent(this@PeopleActivity, FilmsAndSeriesActivity::class.java)
                                            intent.putExtra(Constants.ConstantsFilms.BASE_FILM_KEY, result)
                                            intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 1)
                                            startActivity(intent)
                                        }
                                    }
                                }
                    }
                    binding.rvCardsListSeries.apply {
                        layoutManager = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                                adapter = pessoa.tv_credits?.let { it1 ->
                                    it1.cast?.let { it2 ->
                                        CastDetailsAdapter(it2){ result->
                                            val intent = Intent(this@PeopleActivity, FilmsAndSeriesActivity::class.java)
                                            intent.putExtra(Constants.ConstantsFilms.BASE_SERIE_KEY, result)
                                            intent.putExtra(Constants.ConstantsFilms.ID_FRAGMENTS, 2)
                                            startActivity(intent)
                                        }
                                    }
                                }

                    }
                }
            }
        }
    }
}