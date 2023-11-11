package com.atom.android.lebo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val dateOfBirth: String,
    val email: String,
    val gender: String,
    val image: String,
    val phone: String,
    val updatedAt: String,
    val createdAt: String,
    val confirmEmail: Int
) : Parcelable
