package com.example.challengechapter7.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentProfileBinding
import com.example.challengechapter7.datastore.UserLoginManager
import com.example.challengechapter7.model.RequestUser
import com.example.challengechapter7.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var profileFragmentBinding: FragmentProfileBinding? = null
    private lateinit var userLoginManager: UserLoginManager
    private lateinit var takeImage: ActivityResultLauncher<Intent>
    private lateinit var userViewModel : UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takeImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
            if (data.resultCode == Activity.RESULT_OK) {
                handleImageTakenFromCamera(data.data)
            }
        }
        val binding = FragmentProfileBinding.bind(view)
        profileFragmentBinding = binding

        initField()

        //3 action button
        profileFragmentBinding!!.profileButtonLogout.setOnClickListener {
            logout()
        }
        profileFragmentBinding!!.profileButtonUpdate.setOnClickListener {
            updateData()
        }
        profileFragmentBinding!!.buttonEditProfileImage.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("UBAH FOTO PROFILE")
                .setMessage("Pilih metode untuk mengambil foto profile yang baru")
                .setNeutralButton("Batal"){ dialogIterface : DialogInterface, _ : Int ->
                    dialogIterface.dismiss()
                }.setPositiveButton("Penyimpanan"){ _: DialogInterface, _ : Int ->
                    openImageGallery()
                }
                .setNegativeButton("Kamera"){ _ : DialogInterface, _ : Int ->
                    openCamera()
                }.show()
        }
    }

    private fun logout() {
        userLoginManager = UserLoginManager(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah anda yakin ingin logout?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                GlobalScope.launch {
                    userLoginManager.clearDataLogin()
                }
                val mIntent =activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

    private fun updateData() {
        userLoginManager = UserLoginManager(requireContext())
        var id = ""
        val alamat = profileFragmentBinding!!.profileAlamat.text.toString()
        val email = profileFragmentBinding!!.profileEmail.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val username = profileFragmentBinding!!.profileUsername.text.toString()
        val tanggalLahir = profileFragmentBinding!!.profileTanggalLahir.text.toString()
        val password = profileFragmentBinding!!.profilePassword.text.toString()
        val namaLengkap = profileFragmentBinding!!.profileNamaLengkap.text.toString()
        //get id for current user
        userLoginManager.IDuser.asLiveData().observe(viewLifecycleOwner){
            id = it.toString()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Update data")
            .setMessage("Yakin ingin mengupdate data?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                userViewModel.updateDataUser(id, RequestUser(
                    alamat,
                    email,
                    image,
                    namaLengkap,
                    password,
                    tanggalLahir,
                    username
                ))
                Toast.makeText(requireContext(), "Update data berhasil", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    userLoginManager.saveDataLogin(
                        alamat,
                        email,
                        id,
                        image,
                        namaLengkap,
                        password,
                        tanggalLahir,
                        username
                    )
                }

                //restart activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

    private fun initField() {
        userLoginManager = UserLoginManager(requireContext())

        userLoginManager.imageProfile.asLiveData().observe(viewLifecycleOwner){
            if(it.isNullOrEmpty()){
                userLoginManager.image.asLiveData().observe(viewLifecycleOwner){ result ->
                    Glide.with(profileFragmentBinding!!.profileImage.context)
                        .load(result)
                        .error(R.drawable.profile_photo)
                        .override(100, 100)
                        .into(profileFragmentBinding!!.profileImage)
                }
            }else{
                profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(it))
            }
        }

        userLoginManager.name.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileNamaLengkap.setText(it.toString())
        }
        userLoginManager.dateOfBirth.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileTanggalLahir.setText(it.toString())
        }
        userLoginManager.address.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileAlamat.setText(it.toString())
        }
        userLoginManager.email.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileEmail.setText(it.toString())
        }
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profileUsername.setText(it.toString())
        }
        userLoginManager.password.asLiveData().observe(viewLifecycleOwner){
            profileFragmentBinding!!.profilePassword.setText(it.toString())
            profileFragmentBinding!!.profileKonfirmasiPassword.setText(it.toString())
        }
    }

    //convert a bitmap to string
    private fun convertBitMapToString(input: Bitmap): String? {
        val byteArray = ByteArrayOutputStream()
        input.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val b: ByteArray = byteArray.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    //convert a string to bitmap
    private fun convertStringToBitmap(input: String?): Bitmap? {
        val byteArray1: ByteArray = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            byteArray1, 0,
            byteArray1.size
        )
    }

    //function to open gallery and set profile image
    private val bitmapResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            val originBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, result)
            userLoginManager = UserLoginManager(requireContext())
            val editedBitmap : Bitmap

            if (originBitmap.width >= originBitmap.height){
                editedBitmap = Bitmap.createBitmap(
                    originBitmap,
                    originBitmap.width /2 - originBitmap.height /2,
                    0,
                    originBitmap.height,
                    originBitmap.height
                )

            }else{
                editedBitmap = Bitmap.createBitmap(
                    originBitmap,
                    0,
                    originBitmap.height /2 - originBitmap.width /2,
                    originBitmap.width,
                    originBitmap.width
                )
            }
            val bitmaps = Bitmap.createScaledBitmap(editedBitmap, 720, 720, true)
            val stringResult = convertBitMapToString(bitmaps)!!

            GlobalScope.launch {
                userLoginManager.setImageProfile(stringResult)
            }
            profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(stringResult))
            Toast.makeText(requireContext(), "Foto profile berhasil diupdate", Toast.LENGTH_SHORT).show()
        }
    private fun openImageGallery(){
        bitmapResult.launch("image/*")
    }


    //these 2 functions and cameraResult will handle "set profile image from camera" features
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takeImage.launch(cameraIntent)
    }
    private fun handleImageTakenFromCamera(a : Intent?){
        val originBitmap = a?.extras?.get("data") as Bitmap
        val editedBitmap : Bitmap

        if (originBitmap.width >= originBitmap.height){
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                originBitmap.width /2 - originBitmap.height /2,
                0,
                originBitmap.height,
                originBitmap.height
            )

        }else{
            editedBitmap = Bitmap.createBitmap(
                originBitmap,
                0,
                originBitmap.height /2 - originBitmap.width /2,
                originBitmap.width,
                originBitmap.width
            )
        }

        val stringResult = convertBitMapToString(editedBitmap)
        GlobalScope.launch {
            userLoginManager.setImageProfile(stringResult!!)
        }
        profileFragmentBinding!!.profileImage.setImageBitmap(convertStringToBitmap(stringResult))
        Toast.makeText(requireContext(), "Foto profile berhasil diupdate", Toast.LENGTH_SHORT).show()
    }
}