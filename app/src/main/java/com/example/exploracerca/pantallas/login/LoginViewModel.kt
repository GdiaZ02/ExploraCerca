package com.example.exploracerca.pantallas.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploracerca.modelo.Usuario
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

    class LoginViewModel: ViewModel() {

        private val auth: FirebaseAuth = Firebase.auth
        private val _loading = MutableLiveData(false)

        fun signInWithEmailAndPassword(email: String, password: String, home: ()-> Unit) = viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Login", "SignInEmailPass entra el usuario")
                            home()
                        } else {
                            Log.d("Login", "SignInEmailPass: ${task.result.toString()}")
                        }
                    }
            }catch (ex: Exception){
                Log.d("Login", "SignInEmailPass: ${ex.message}")
            }
        }
        fun createUserWithEmailAndPass(
            email: String,
            password: String,
            home: () -> Unit
        ){
            if (_loading.value == false){
                _loading.value = true
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //si crea bien el usuario lo registro, le mando a home y me guardo su nombre de usuario
                            val displayName =
                                task.result.user?.email?.split("@")?.get(0)
                            createUser(displayName)
                            home()
                        } else {
                            Log.d("Login", "CreateUserEmailPass: ${task.result.toString()}")
                        }
                        _loading.value = false
                    }
            }
        }
        //Función para crear usuarios en firebase y guardar mas campos que usuario y contraseña
        private fun createUser(displayName: String?) {
            val userId = auth.currentUser?.uid

            val user = Usuario(
                userId = userId.toString(),
                displayName = displayName.toString(),
                comentarios = "Reservar",
                id = null
            ).toMap()
            FirebaseFirestore.getInstance().collection("users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("Create", "Usuario creado ${it.id}")
                }.addOnFailureListener {
                    Log.d("Create", "ERROR Usuario creado ${it}")
                }
        }

    }
