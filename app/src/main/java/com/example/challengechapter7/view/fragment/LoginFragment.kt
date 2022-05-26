package com.example.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentLoginBinding
import com.example.challengechapter7.datastore.UserLoginManager
import com.example.challengechapter7.model.GetAllUserResponseItem
import com.example.challengechapter7.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var loginFragmentBinding: FragmentLoginBinding? = null
    private lateinit var userLoginManager: UserLoginManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        loginFragmentBinding = binding

        loginFragmentBinding!!.buttonLogin.setOnClickListener {
            initUserApiViewModel()
        }

        loginFragmentBinding!!.loginRegisteringAccount.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun initUserApiViewModel() {
        val viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModelUser.user.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                loginAuth(it)
            }
        }
    }

    private fun loginAuth(listUser: List<GetAllUserResponseItem>) {
        val inputanEmail = loginFragmentBinding!!.loginInputEmail.text.toString()
        val inputanPassword = loginFragmentBinding!!.loginInputPassword.text.toString()
        userLoginManager = UserLoginManager(requireContext())

        if (inputanEmail.isNotEmpty() && inputanPassword.isNotEmpty()) {
            for (i in listUser.indices) {
                if (inputanEmail == listUser[i].email && inputanPassword == listUser[i].password) {
                    Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                    //saving data to datastore
                    GlobalScope.launch {
                        userLoginManager.setBoolean(true)
                        userLoginManager.saveDataLogin(
                            listUser[i].alamat,
                            listUser[i].email,
                            listUser[i].id,
                            listUser[i].image,
                            listUser[i].name,
                            listUser[i].password,
                            listUser[i].tanggal_lahir,
                            listUser[i].username
                        )
                    }
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginFragment_to_homeFragment)
                    break
                } else if (i == listUser.lastIndex && inputanEmail != listUser[i].email && inputanPassword != listUser[i].password) {
                    Toast.makeText(requireContext(), "Email/password salah", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Email dan password harus diisi", Toast.LENGTH_SHORT)
                .show()
        }
    }
}