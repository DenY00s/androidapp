package com.novikov.myfirstapp.model

import androidx.annotation.DrawableRes

data class PhoneModel(
    val modelName: String,
    val cameraRating: Double,
    val releaseYear: Int,
    @DrawableRes val imageResId: Int,
)