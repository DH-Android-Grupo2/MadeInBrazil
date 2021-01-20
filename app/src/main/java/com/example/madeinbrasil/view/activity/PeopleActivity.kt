package com.example.madeinbrasil.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.madeinbrasil.adapter.CastDetailsAdapter
import com.example.madeinbrasil.adapter.FilmsSeriesFromUserAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.database.entities.cast.CastEntity
import com.example.madeinbrasil.databinding.ActivityPeopleBinding
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.utils.Constants
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_ACTOR_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_FILM_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.BASE_SERIE_KEY
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.ID_FRAGMENTS
import com.example.madeinbrasil.utils.Constants.ConstantsFilms.VALUE
import com.example.madeinbrasil.viewModel.PersonViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PeopleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPeopleBinding
    private var people: Cast? = null
    private var peopleSerie: com.example.madeinbrasil.model.serieDetailed.Cast? = null
    private lateinit var viewModelPeople: PersonViewModel
    private var position: Int = 0
    private val date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        position = intent.getIntExtra(VALUE, 0)

        when(position) {
            1 -> {
                startActorMovie()
            }
            2 -> {
                startActorSerie()
            }
        }

        binding.ivArrowBackFilmsSeries.setOnClickListener {
            finish()
        }
    }

    private fun startActorMovie() {
        people = intent.getParcelableExtra(BASE_ACTOR_KEY)
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val dateYear =  dateFormat.format(date)

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

                    binding.rvCardsListFilmes.apply {
                        layoutManager = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = pessoa.movie_credits?.let { it1 ->
                            it1.cast?.let { it2 ->
                                FilmsSeriesFromUserAdapter(it2){ result->
                                    val intent = Intent(this@PeopleActivity, FilmsAndSeriesActivity::class.java)
                                    intent.putExtra(BASE_FILM_KEY, result)
                                    intent.putExtra(ID_FRAGMENTS, 1)
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
                                    intent.putExtra(BASE_SERIE_KEY, result)
                                    intent.putExtra(ID_FRAGMENTS, 2)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startActorSerie() {
        peopleSerie = intent.getParcelableExtra(BASE_ACTOR_KEY)
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val dateYear =  dateFormat.format(date)

        viewModelPeople = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewModelPeople.getPerson(peopleSerie?.id)

        binding.apply {
            tvNameFilmsSeries.text = peopleSerie?.name

            Glide.with(binding.root.context)
                    .load(peopleSerie?.profilePath)
                    .into(ivBannerFilmsSeries)
            Glide.with(binding.root.context)
                    .load(peopleSerie?.profilePath)
                    .into(ivBackDropFilmSeries)

            viewModelPeople.personSucess.observe(this@PeopleActivity) {
                it?.let { pessoa ->
//                    val people = CastEntity(pessoa.id, pessoa.biography, pessoa.birthday, pessoa.name, pessoa.profile_path)
//                    viewModelPeople.insertPeople(people)

                    pessoa.birthday?.let {ano ->
                        pessoa.birthday = "${(ano.toInt().let { it1 -> dateYear.toInt().minus(it1) })} anos"
                    }

                    tvDescriptionTextFilmsSeries.text = pessoa.biography
                    tvYearFilmsSeries.text = pessoa.birthday

                    binding.rvCardsListFilmes.apply {
                        layoutManager = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = pessoa.movie_credits?.let { it1 ->
                            it1.cast?.let { it2 ->
                                FilmsSeriesFromUserAdapter(it2){ result->
                                    val intent = Intent(this@PeopleActivity, FilmsAndSeriesActivity::class.java)
                                    intent.putExtra(BASE_FILM_KEY, result)
                                    intent.putExtra(ID_FRAGMENTS, 1)
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
                                    intent.putExtra(BASE_SERIE_KEY, result)
                                    intent.putExtra(ID_FRAGMENTS, 2)
                                    startActivity(intent)
                                }
                            }
                        }
                    }

                    lifecycleScope.launch {
                        val dbPeople = MadeInBrazilDatabase.getDatabase(this@PeopleActivity).peopleDao()

                    }
                }
            }
        }
    }
}