package com.example.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.challengechapter7.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var loginFragmentBinding : FragmentLoginBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentLoginBinding.bind(view)
        loginFragmentBinding = binding
    }
}