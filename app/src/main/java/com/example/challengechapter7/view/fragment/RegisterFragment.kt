package com.example.challengechapter7.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentRegisterBinding
import com.example.challengechapter7.model.RequestUser
import com.example.challengechapter7.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var registerFragmentBinding: FragmentRegisterBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        registerFragmentBinding = binding

        registerFragmentBinding!!.buttonRegister.setOnClickListener {
            tambahUser()
        }
    }

    private fun tambahUser() {
        val viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        val nama = registerFragmentBinding!!.registerInputNama.text.toString()
        val username = registerFragmentBinding!!.registerInputUsername.text.toString()
        val alamat = registerFragmentBinding!!.registerInputAlamat.text.toString()
        val tanggalLahir = registerFragmentBinding!!.registerInputTanggalLahir.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val password = registerFragmentBinding!!.registerInputPassword.text.toString()
        val email = registerFragmentBinding!!.registerInputEmail.text.toString()
        val konfirmasiPassword = registerFragmentBinding!!.registerInputKonfirmasiPassword.text.toString()

        //check if all fields is not empty
        if (nama.isNotEmpty() && username.isNotEmpty() && alamat.isNotEmpty() &&
            tanggalLahir.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() &&
            konfirmasiPassword.isNotEmpty()
        ) {
            //check similarity of password and konfirmasiPassword
            if (password == konfirmasiPassword) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModelUser.insertNewUser(RequestUser(alamat, email, image, nama, password, tanggalLahir, username)).also {
                        Toast.makeText(requireContext(), "Berhasil register", Toast.LENGTH_SHORT).show()
                    }
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(),"Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}