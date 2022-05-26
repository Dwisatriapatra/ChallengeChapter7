package com.example.challengechapter7.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAllUserResponseItem(
    val alamat: String,
    val email: String,
    val id: String,
    val image: String,
    val name: String,
    val password: String,
    val tanggal_lahir: String,
    val username: String
) : Parcelable