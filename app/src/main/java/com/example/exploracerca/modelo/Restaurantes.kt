package com.example.exploracerca.modelo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Restaurantes(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    @StringRes val restauranteDetails: Int,
    val Capacidad: Int,
    val latitud: Double,
    val longitud: Double
)