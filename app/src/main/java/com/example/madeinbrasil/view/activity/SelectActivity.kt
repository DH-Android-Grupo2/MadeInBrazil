package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivitySelectBinding
import com.example.madeinbrasil.model.result.Genre
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_select.view.*

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedGenres = mutableListOf<Genre>()
        val button = findViewById<Button>(R.id.btContinueGender)
        val chip18drama : Chip = findViewById(R.id.chip18Drama)
        val chip28action : Chip = findViewById(R.id.chip28Action)
        val chip27horror : Chip = findViewById(R.id.chip27Horror)
        val chip10751family: Chip = findViewById(R.id.chip10751Family)
        val chip16animation: Chip = findViewById(R.id.chip16Animation)
        val chip99doc: Chip = findViewById(R.id.chip99Documentary)
        val chip35comedy: Chip = findViewById(R.id.chip35Comedy)
        val chip80crime: Chip = findViewById(R.id.chip80Crime)
        val chip14fantasy: Chip = findViewById(R.id.chip14Fantasy)
        val chip9648mistery: Chip = findViewById(R.id.chip9648Mistery)
        val chip10752war: Chip = findViewById(R.id.chip10752War)
        val chip878scifi: Chip = findViewById(R.id.chip878Scifi)
        val chip53Thriller: Chip = findViewById(R.id.chip53Thriller)
        val chip10402music: Chip = findViewById(R.id.chip10402Music)
        val chip10770tvmovie: Chip = findViewById(R.id.chip10770TvMovie)


        chip18drama.setOnClickListener {
           val genre18 = Genre(18, "Drama")
            selectedGenres.add(genre18)
        }


        chip28action.setOnClickListener {
            val genre28 = Genre(28, "Action")
            selectedGenres.add(genre28)
        }

        chip27horror.setOnClickListener {
            val genre27 = Genre(27, "Horror")
            selectedGenres.add(genre27)
        }

        chip10751family.setOnClickListener {
            val genre10751 = Genre(10751, "Family")
            selectedGenres.add(genre10751)
        }

        chip16animation.setOnClickListener {
            val genre16 = Genre(16, "Animation")
            selectedGenres.add(genre16)
        }

        chip99doc.setOnClickListener {
            val genre99 = Genre(99, "Documentary")
            selectedGenres.add(genre99)
        }

        chip35comedy.setOnClickListener {
            val genre35 = Genre(35, "Comedy")
            selectedGenres.add(genre35)
        }

        chip80crime.setOnClickListener {
            val genre80 = Genre(80, "Crime")
            selectedGenres.add(genre80)
        }

        chip14fantasy.setOnClickListener {
            val genre14 = Genre(14, "Fantasy")
            selectedGenres.add(genre14)
        }

        chip9648mistery.setOnClickListener {
            val genre9648 = Genre(9648, "Mistery")
            selectedGenres.add(genre9648)
        }

        chip10752war.setOnClickListener {
            val genre10752 = Genre(10752, "War")
            selectedGenres.add(genre10752)
        }

        chip878scifi.setOnClickListener {
            val genre878 = Genre(878, "Scifi")
            selectedGenres.add(genre878)
        }

        chip53Thriller.setOnClickListener {
            val genre53 = Genre(53, "Thriller")
            selectedGenres.add(genre53)
        }

        chip10402music.setOnClickListener {
            val genre10402 = Genre(10402, "Music")
            selectedGenres.add(genre10402)
        }

        chip10770tvmovie.setOnClickListener {
            val genre10770 = Genre(10770, "Tv Movie")
            selectedGenres.add(genre10770)
        }




        button.setOnClickListener {
            startMenuActivity(this@SelectActivity)
        }

    }

    fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)

    }

}