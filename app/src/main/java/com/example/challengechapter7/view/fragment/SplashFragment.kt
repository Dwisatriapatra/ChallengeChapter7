package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentSplashBinding
import com.example.challengechapter7.datastore.UserLoginManager

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var splashFragmentBinding: FragmentSplashBinding? = null
    private lateinit var userLoginManager: UserLoginManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)
        splashFragmentBinding = binding
        userLoginManager = UserLoginManager(requireContext())

        //animation handler
        Handler(Looper.getMainLooper()).postDelayed({
            userLoginManager.boolean.asLiveData().observe(viewLifecycleOwner) {
                if (it == true) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, 3000)
    }
}