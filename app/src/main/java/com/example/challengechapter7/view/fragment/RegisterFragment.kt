package com.example.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var registerFragmentBinding: FragmentRegisterBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        registerFragmentBinding = binding
    }
}