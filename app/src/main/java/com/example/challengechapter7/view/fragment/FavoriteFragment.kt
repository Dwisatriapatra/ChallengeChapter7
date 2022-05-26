package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var favoriteFragmentBinding: FragmentFavoriteBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        favoriteFragmentBinding = binding
    }
}