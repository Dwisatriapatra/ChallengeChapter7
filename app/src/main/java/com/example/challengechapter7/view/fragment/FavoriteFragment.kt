package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentFavoriteBinding
import com.example.challengechapter7.view.adapter.FavoriteFilmAdapter
import com.example.challengechapter7.viewmodel.FavoriteFilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteFilmAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        favoriteFragmentBinding = binding

        initView()
    }
    private fun initView() {
        adapter = FavoriteFilmAdapter {
            val clickedGhibliFilm = bundleOf("FAVORITEGHIBLIFILMDATA" to it)
            Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_detailFragment, clickedGhibliFilm)
        }
        favoriteFragmentBinding!!.rvFavoriteGhibliFilm.layoutManager = LinearLayoutManager(requireContext())
        favoriteFragmentBinding!!.rvFavoriteGhibliFilm.adapter = adapter


        val viewModelFavoriteFilm = ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
        viewModelFavoriteFilm.favoriteFilm.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.setDataFavoriteGhibliFilms(it)
                adapter.notifyDataSetChanged()
            }else{
                favoriteFragmentBinding!!.favoriteNoDataAnimation.setAnimation(R.raw.no_favorite_film_data)
            }
        }

    }
}