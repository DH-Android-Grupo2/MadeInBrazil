package com.example.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.ActivityFavoriteBinding
import com.example.madeinbrasil.view.adapter.MovieAdapter
import com.example.madeinbrasil.view.model.Movie

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
        initRecyclerView()
    }

    private fun initSpinner() {
        binding.spinner.apply {

            adapter = ArrayAdapter.createFromResource(this@FavoriteActivity,
                    R.array.spinner_options,
                    R.layout.custom_spinner).apply {
                setDropDownViewResource(R.layout.custom_spinner_dropdown)
            }

           // setBackgroundResource(R.drawable.custom_spinner)
        }
    }

    private fun initRecyclerView() {
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            adapter = MovieAdapter(createMovies() + createSeries())
        }
    }

    private fun createMovies(): List<Movie> {
        return mutableListOf<Movie>(
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRGLE0E0T3yn5N05cp_Raz69Sa1MzrJGTFrG2UfYECKE4D2j3lZ", "Aquarius"),
                Movie("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR8Q2duuoMnC490_Il_h5mVHo-w8Vm9dCdb_zVWEzOQm34LnCox", "Praia do futuro"),
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSsAdymeDzx2f42k3u9jrIGQiJfqCdDRE0dpgUSo7QtoBVuSYPY", "Assalto ao banco central"),
                Movie("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT79pGe5Ui8Nc-jaYDOMQLwFFEJJ34ABvHRC4hp0WBnLUZ9U5wy", "Cidadede de Deus"),
                Movie("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR_O3HYJ5IDhVrtCfyO8TMEK0Rmwjuu4OM0PcJTn9ZMl4NIy3o-", "2 filhos de São Frnacisco"),
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQx7-rNNsDCMkhKtzceWKxF8oz0eiAfWL2z1jaDtj63AODiSMYU", "Tropa de elite"),
                Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQN6qHAO-f4Ef9Yt0qUvHyxKhi9OR-uiNlEHyMhk1LJHz70oSbP", "Meu nome não é Johnny"),
                Movie("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRKkWSIuxYtelH7IDkgpMNvPpSpfp11gDLeLyqTypKp_ace7FC5", "Faroeste caboclo"),
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcT_0i9ymXw9ZzyFvzrM6SZUgDTkfex8PUUsRjhOyizJKqBidf51", "Minha mãe é uma peça"),
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQEs-mmvyrQhA-k4fc5xLPqE6UPCTUDTX93S2LNytY8zKIFC4PR", "O lobo atrás da porta")
        )
    }

    private fun createSeries(): List<Movie> {
        return mutableListOf<Movie>(
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSjSzorJlTWIqpHZTa9wzqRg8ulXlj5zyRjVxfE1MsZX9xqghC7","Justiça"),
                Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQw1hD_qiGOlGsuTWobQqggxTZZKwcy503ENVulazjdIlsRVcV_", "O canto da sereia"),
                Movie("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSw0sMkjVuJhQeTC27h3juFhNei6YMQU1I6bK0dOLPuynunh2nW", "1 contra todos"),
                Movie("https://img.travessa.com.br/DVD/GR/da/dab6e1db-b12a-4537-93bb-f1b2af8f17f4.jpg", "Pé na cova"),
                Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZIppOSz8FcUff98BHVH7vMZzehK1iE4N0jWwN1eSCcbD1b87O", "Sai be baixo"),
                Movie("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQh0rS-mjtYpPT9iX0m6LjQOgfbZhpNZi5a-73tdZzygUTvAM2C","Sintonia"),
                Movie("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQXZY1O2CxU0KZrVKWJPM67lXDw6u-mDrTm-TWeovFhGWQsX9sO", "A grande Familia"),
                Movie("https://occ-0-1168-299.1.nflxso.net/dnm/api/v6/evlCitJPPCVCry0BZlEFb5-QjKc/AAAABQGi-A7SzkLfCVFlPVTaVd6ynkY_H0BTp-RqUki87A2v1tQ5rKYHV9GxKDyjdmK3BB1MT3T_hIebaIXR9A8oK6Uv9LeLCWwrDQHj0b6SjNjk8W5SF6DVWWc-idM.jpg?r=e13", "Spectrus"),
                Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_qmG20AGA7iJ-pbqcuXLUPnrWDcUbDqh6RLksomrrSN6TjsMs", "Irmandade"),
                Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTu4T8IR3AsXyL6awSI9Sv-gBM73ZGIK3M6CisoRYtVJQv67BY5", "Sob pressão")
        )
    }
}