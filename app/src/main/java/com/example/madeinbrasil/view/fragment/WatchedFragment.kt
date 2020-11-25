package com.example.madeinbrasil.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.madeinbrasil.R
import com.example.madeinbrasil.databinding.FragmentWatchedBinding


class WatchedFragment : Fragment() {
    private lateinit var binding: FragmentWatchedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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