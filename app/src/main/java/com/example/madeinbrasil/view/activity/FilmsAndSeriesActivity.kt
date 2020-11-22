package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madeinbrasil.R
import kotlinx.android.synthetic.main.activity_films_and_series.*

class FilmsAndSeriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_and_series)

        ratingBarFilmsSeries.numStars = 5
        ratingBarFilmsSeries.rating = 3.5f
    }
}