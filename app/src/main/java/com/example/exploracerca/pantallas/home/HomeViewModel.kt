package com.example.exploracerca.pantallas.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.exploracerca.data.RestaurantesData
import com.example.exploracerca.modelo.Restaurantes
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    var restaurante: String = ""
    var date: String = ""

    private val firestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(
        RestaurantesUiState(
            RestaurantesList = RestaurantesData.getRestauranteData(),
            currentRestaurante = RestaurantesData.getRestauranteData().getOrElse(0) {
                RestaurantesData.defaultRestaurante
            }
        )
    )

    val uiState: StateFlow<RestaurantesUiState> = _uiState

    fun updateCurrentRestaurante(selectedRestaurantes: Restaurantes) {
        _uiState.update {
            it.copy(currentRestaurante = selectedRestaurantes)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }

    // Función para crear una reserva en Firestore
    fun crearReservaFirestore(
        nombreRestaurante: String,
        fecha: String,
        numPersonas: Int,
        telefono: String

    ) {
        val reserva = hashMapOf(
            "nombreRestaurante" to nombreRestaurante,
            "fecha" to fecha,
            "numPersonas" to numPersonas,
            "telefono" to telefono,
            "horaReservado" to FieldValue.serverTimestamp()
        )

        firestore.collection("reservas")
            .add(reserva)
            .addOnSuccessListener { documentReference ->
                // La reserva se ha guardado exitosamente en Firestore
                Log.d(TAG, "Reserva creada con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Se produjo un error al guardar la reserva en Firestore
                Log.w(TAG, "Error al crear la reserva", e)
            }
    }
}

data class RestaurantesUiState(
    val RestaurantesList: List<Restaurantes> = emptyList(),
    val currentRestaurante: Restaurantes = RestaurantesData.defaultRestaurante,
    val isShowingListPage: Boolean = true

)
