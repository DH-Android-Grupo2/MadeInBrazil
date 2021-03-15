package com.dh.madeinbrasil.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dh.madeinbrasil.R
import com.dh.madeinbrasil.adapter.SelectedShowsAdapter
import com.dh.madeinbrasil.databinding.ActivityCreateListBinding
import com.dh.madeinbrasil.model.customLists.ListWithMedia
import com.dh.madeinbrasil.model.customLists.firebase.CustomList
import com.dh.madeinbrasil.model.customLists.firebase.Media
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_MOVIES
import com.dh.madeinbrasil.utils.Constants.ConstantsFilms.SELECTED_SERIES
import com.dh.madeinbrasil.utils.Constants.CustomLists.LIST
import com.dh.madeinbrasil.view.fragment.SelectMovieFragment
import com.dh.madeinbrasil.view.fragment.SelectSerieFragment
import com.dh.madeinbrasil.viewModel.CustomListViewModel
import com.dh.madeinbrasil.viewModel.SelectMovieViewModel
import com.dh.madeinbrasil.viewModel.SelectSerieViewModel
import kotlinx.android.synthetic.main.activity_create_list.*

class CreateListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateListBinding
    private lateinit var selectSerieViewModel: SelectSerieViewModel
    private lateinit var selectMovieViewModel: SelectMovieViewModel
    private lateinit var customListViewModel: CustomListViewModel
    private var selectedMovies: MutableList<String> = mutableListOf()
    private var selectedSeries: MutableList<String> = mutableListOf()
    private var listForUpdate: ListWithMedia? = null
    private var selectedMediaList: MutableList<Media> = mutableListOf()

    private val selectedShowsAdapter by lazy {
        SelectedShowsAdapter() {
            // checa se o array de ids da série contém o id em questão, caso não
            // tenha, então, se trata de um filme
            if (selectedSeries.contains(it.id))
                selectedSeries.remove(it.id)
            else
                selectedMovies.remove(it.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customListViewModel = ViewModelProvider(this).get(CustomListViewModel::class.java)
        selectSerieViewModel = ViewModelProvider(this).get(SelectSerieViewModel::class.java)
        selectMovieViewModel = ViewModelProvider(this).get(SelectMovieViewModel::class.java)
        setupRecyclerView()
        setupButtonListeners()
        setupShowClickListeners()
        setupFormFieldsListeners()

        intent.getParcelableExtra<ListWithMedia>(LIST)?.let {
            populateListInfo(it)
            listForUpdate = it
            selectedMediaList.addAll(it.mediaList)
            binding.btnCreateList.text = getString(R.string.string_update_list_button)
        } ?: run {
            binding.btnCreateList.text = getString(R.string.string_create_list_button)
        }
    }

    private fun setupRecyclerView() {
        binding.rvSelectedShows.apply {
            layoutManager = LinearLayoutManager(this@CreateListActivity, RecyclerView.HORIZONTAL, false)
            adapter = selectedShowsAdapter
        }
    }

    private fun setupButtonListeners() {
        binding.imBackButton.setOnClickListener {
            super.onBackPressed()
        }

        binding.imAddMovie.setOnClickListener {
            val fragment = SelectMovieFragment()
            fragment.arguments = Bundle().apply {
                putStringArray(SELECTED_MOVIES, selectedMovies.toTypedArray())
            }
            fragment.show(supportFragmentManager, null)
        }

        binding.imAddSerie.setOnClickListener {
            val fragment = SelectSerieFragment()
            fragment.arguments = Bundle().apply {
                putStringArray(SELECTED_SERIES, selectedSeries.toTypedArray())
            }
            fragment.show(supportFragmentManager, null)
        }

        binding.btnCreateList.setOnClickListener {
//            saveListDataToDB()
            if (intent.getParcelableExtra<ListWithMedia>(LIST) != null)
                updateList(selectedShowsAdapter.list)
            else
                saveList()
        }
    }

    private fun setupShowClickListeners() {

        selectMovieViewModel.clickedMovieItem.observe(this, {
            if (selectedMovies.contains(it.id)) {
                selectedMovies.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            } else {
                selectedMovies.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        })

        selectSerieViewModel.clickedSerieItem.observe(this) {
            if (selectedSeries.contains(it.id)) {
                selectedSeries.remove(it.id)
                selectedShowsAdapter.deleteItem(it)
            } else {
                selectedSeries.add(it.id)
                selectedShowsAdapter.addItem(it)
            }
        }

    }

    private fun setupFormFieldsListeners() {

        val listNameField: EditText = binding.teetName
        val createListBtn = binding.btnCreateList

        listNameField.addTextChangedListener {
            it?.let {
                createListBtn.isEnabled = it.trim().isNotEmpty()
            }
        }
    }

    private fun populateListInfo(list: ListWithMedia) = with(binding) {
        with(list.list) {
            teetName.text = Editable.Factory.getInstance().newEditable(name)
            teetDescription.text = Editable.Factory.getInstance().newEditable(description)

            selectedMovies = movies as MutableList<String>
            selectedSeries = series as MutableList<String>
        }

        selectedShowsAdapter.list = list.mediaList as MutableList<Media>
    }

    private fun saveList() = with(binding) {

        toogleActionProgress()

        val customList = CustomList(teetName.text.toString(), teetDescription.text.toString(), selectedMovies, selectedSeries)
        customListViewModel.createList(customList, selectedShowsAdapter.list)

        customListViewModel.listSucess.observe(this@CreateListActivity, {
            finish()
        })

        customListViewModel.listFailure.observe(this@CreateListActivity, {
            Toast.makeText(this@CreateListActivity, it, Toast.LENGTH_SHORT).show()
            toogleActionProgress()
        })

    }

    private fun updateList(selectedShows: List<Media>) = with(binding) {
        toogleActionProgress()
        lateinit var newSelected: List<Media>

            if (selectedMediaList.isNotEmpty() && selectedShows.isNotEmpty())
                newSelected = selectedShows.minus(selectedMediaList)
            else
                if (selectedShows.isNotEmpty())
                    newSelected = selectedShows
                else
                    newSelected = listOf()


        listForUpdate?.let {

             it.apply {
                list.apply {
                    name = teetName.text.toString();
                    description = teetDescription.text.toString();
                    movies = selectedMovies;
                    series = selectedSeries
                }

                 mediaList = newSelected
            }

            customListViewModel.updateList(it)
        }

        customListViewModel.listSucess.observe(this@CreateListActivity, {
            Toast.makeText(this@CreateListActivity, it, Toast.LENGTH_SHORT).show()
            finish()
        })

        customListViewModel.listFailure.observe(this@CreateListActivity, {
            Toast.makeText(this@CreateListActivity, it, Toast.LENGTH_SHORT).show()
            toogleActionProgress()
        })


    }

    private fun toogleActionProgress()  = with(binding) {
        if(processingBar.visibility == View.GONE)
            processingBar.visibility = View.VISIBLE
        else
            processingBar.visibility = View.GONE

        if(btnCreateList.visibility == View.GONE)
            btnCreateList.visibility = View.VISIBLE
        else
            btnCreateList.visibility = View.GONE
    }

//    private fun saveListDataToDB() {
//
//        val movieList = mutableListOf<ListMovieItem>()
//        val serieList = mutableListOf<ListSerieItem>()
//
//        selectedShowsAdapter.list.forEach {
//            if (selectedSeries.contains(it.id))
//                serieList.add(ListSerieItem(it.id, it.title, it.backdropPath, it.originalTitle))
//            else
//                movieList.add(ListMovieItem(it.id, it.title, it.backdropPath, it.originalTitle))
//        }
//
//        customListViewModel.createCustomList(ListWithMedia(
//            CustomList(0, binding.teetName.text.toString(), binding.teetDescription.text.toString(), 0),
//                movieList,
//                serieList)
//        )
//
//        setResult(RESULT_OK)
//        finish()
//    }

}
