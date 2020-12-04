package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityFilmsAndSeriesBinding
import com.example.madeinbrasil.databinding.ActivityPeopleBinding
import com.example.madeinbrasil.databinding.FragmentHomeBinding
import com.example.madeinbrasil.model.movieCredits.Cast
import com.example.madeinbrasil.model.upcoming.Result
import com.example.madeinbrasil.utils.Constants

class PeopleActivity : AppCompatActivity() {
    private var people: Cast? = null
    private lateinit var binding: ActivityPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        people = intent.getParcelableExtra(Constants.ConstantsFilms.BASE_ACTOR_KEY)

        binding.apply {
            tvNameFilmsSeries.text = people?.name
            tvDescriptionTextFilmsSeries.text = "BIOGRAFIA DA PESSOA"
            Glide.with(binding.root.context)
                .load(people?.profile_path)
                .into(ivBannerFilmsSeries)

        }


    }
}