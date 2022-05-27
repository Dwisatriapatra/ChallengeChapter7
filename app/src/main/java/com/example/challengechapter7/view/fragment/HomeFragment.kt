package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter7.BuildConfig
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentHomeBinding
import com.example.challengechapter7.datastore.UserLoginManager
import com.example.challengechapter7.view.adapter.GhibliFilmsAdapter
import com.example.challengechapter7.viewmodel.GhibliFilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var homeFragmentBinding: FragmentHomeBinding? = null
    private lateinit var userLoginManager: UserLoginManager
    private lateinit var adapter: GhibliFilmsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        homeFragmentBinding = binding

        initView()
    }

    private fun initView() {
        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner) {
            homeFragmentBinding!!.welcomeText.text = "Hello, $it"
        }

        adapter = GhibliFilmsAdapter {
            val clickedGhibliFilm = bundleOf("GHIBLIFILMDATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_detailFragment, clickedGhibliFilm)
        }
        homeFragmentBinding!!.rvGhibliFilm.layoutManager = LinearLayoutManager(requireContext())
        homeFragmentBinding!!.rvGhibliFilm.adapter = adapter

        val viewModelGhibliFilm = ViewModelProvider(this).get(GhibliFilmViewModel::class.java)
        viewModelGhibliFilm.ghibliFilm.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setDataGhibliFilms(it)
                adapter.notifyDataSetChanged()
            }
        }

        homeFragmentBinding!!.homeToProfile.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_profileFragment)
        }

        homeFragmentBinding!!.homeToFavorite.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        if(BuildConfig.FLAVOR == "free"){
            homeFragmentBinding!!.homeToFavorite.isInvisible = true
        }
    }
}