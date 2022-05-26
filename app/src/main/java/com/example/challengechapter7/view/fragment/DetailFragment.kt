package com.example.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var detailFragmentBinding: FragmentDetailBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        detailFragmentBinding = binding
    }
}