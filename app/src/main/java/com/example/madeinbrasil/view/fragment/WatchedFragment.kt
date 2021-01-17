package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madeinbrasil.adapter.FavoriteMidiaAdapter
import com.example.madeinbrasil.adapter.WatchedMidiaAdapter
import com.example.madeinbrasil.database.MadeInBrazilDatabase
import com.example.madeinbrasil.databinding.FragmentWatchedBinding
import kotlinx.coroutines.launch


class WatchedFragment : Fragment() {
    private lateinit var binding: FragmentWatchedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {activity ->
            lifecycleScope.launch {
                val db = MadeInBrazilDatabase.getDatabase(activity).watchedDao()

                binding.rvCardsListWatched.apply {
                    layoutManager = GridLayoutManager(activity, 2)
                    adapter = WatchedMidiaAdapter(db.getMidiaWithWatched())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}