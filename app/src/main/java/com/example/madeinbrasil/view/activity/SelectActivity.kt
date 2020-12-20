package com.example.madeinbrasil.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.get
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
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class SelectActivity : AppCompatActivity() {
    var selectedGenres = mutableListOf<String>()


    private lateinit var binding: ActivitySelectBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btContinueGender.setOnClickListener {
            val selectedButtons = binding.tags.selectedButtons
            selectedGenres.clear()
            selectedButtons.forEach {
                selectedGenres.add("${it.tag} ,")
            }
            Log.i("sdafa","${selectedGenres}")
            startMenuActivity(this@SelectActivity)
        }

    }

    fun startMenuActivity(context: Context) {
        val intent = Intent(context, MenuActivity::class.java)
        intent.putExtra("genreList",GenreSelected(selectedGenres))
        startActivity(intent)

    }



}