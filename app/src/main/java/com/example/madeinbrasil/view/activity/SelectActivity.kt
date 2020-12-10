package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivitySelectBinding
import com.example.madeinbrasil.model.discoverTV.Result
import com.example.madeinbrasil.model.gender.GenreSelected
import com.example.madeinbrasil.model.result.Genre
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_select.view.*

class SelectActivity : AppCompatActivity() {
    var selectedGenres = mutableListOf<String>()


    private lateinit var binding: ActivitySelectBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chipgroup : ChipGroup = findViewById<ChipGroup>(R.id.chipGroupGender)
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



        chip18drama.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip18drama.isChecked){
                selectedGenres.add("18,")
            } else {
                selectedGenres.remove("18,")
            }
        }




        chip28action.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip28action.isChecked){
                selectedGenres.add("28,")
            }else {
                selectedGenres.remove("28,")
            }
        }


        chip27horror.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip27horror.isChecked){
                selectedGenres.add("27,")
            }else {
                selectedGenres.remove("27,")
            }
        }


        chip10751family.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip10751family.isChecked){
                selectedGenres.add("10751,")
            }else {
                selectedGenres.remove("10751,")
            }
        }




        chip16animation.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip16animation.isChecked){
                selectedGenres.add("16,")
            } else {
                selectedGenres.remove("16,")
            }
        }






        chip99doc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip99doc.isChecked){
                selectedGenres.add("99,")
            }else {
                selectedGenres.remove("99,")
            }
        }


        chip35comedy.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip35comedy.isChecked){
                selectedGenres.add("35,")
            }else {
                selectedGenres.remove("35,")
            }
        }




        chip80crime.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip80crime.isChecked){
                selectedGenres.add("80,")
            }else {
                selectedGenres.remove("80,")
            }
        }



        chip14fantasy.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip14fantasy.isChecked){
                selectedGenres.add("14,")
            }else {
                selectedGenres.remove("14,")
            }
        }


        chip9648mistery.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip9648mistery.isChecked){
                selectedGenres.add("9648,")
            }else {
                selectedGenres.remove("9648,")
            }
        }


        chip10752war.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip10752war.isChecked){
                selectedGenres.add("10752,")
            }else {
                selectedGenres.remove("10752,")
            }
        }

        chip878scifi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip878scifi.isChecked){
                selectedGenres.add("878,")
            }else {
                selectedGenres.remove("878,")
            }
        }


        chip53Thriller.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip53Thriller.isChecked){
                selectedGenres.add("53,")
            }else {
                selectedGenres.remove("53,")
            }
        }

        chip10402music.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip10402music.isChecked){
                selectedGenres.add("10402,")
            }else {
                selectedGenres.remove("10402,")
            }
        }


        chip10770tvmovie.setOnCheckedChangeListener { buttonView, isChecked ->
            if (chip10770tvmovie.isChecked){
                selectedGenres.add("10770,")
            }else {
                selectedGenres.remove("10770,")
            }
        }





        button.setOnClickListener {
            startMenuActivity(this@SelectActivity)
        }

    }

    fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("genreList",GenreSelected(selectedGenres))
        startActivity(intent)

    }



}