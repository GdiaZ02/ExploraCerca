package com.example.exploracerca.pantallas.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.exploracerca.R
import com.example.exploracerca.navegacion.ExploraCercaScreens
import androidx.navigation.NavController




@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Card(
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(16.dp)
            .background(Color.White) // Fondo de la card
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)) // Bordes de la card
    ) {
        Image(
            painter = painterResource(id = R.drawable.pngtreeavatar_icon_profile_icon_member_5247852),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp) // Ajusta el ancho de la imagen segÃºn sea necesario
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(stringResource(R.string.iniciaSesion))
                UserForm(isCreateAccount = false) { email, password ->
                    Log.d("Login", "Me logueo con $email y $password")
                    viewModel.signInWithEmailAndPassword(email, password) {
                        navController.navigate(ExploraCercaScreens.HomeScreen.name)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val pregunta =
                        if (showLoginForm.value) stringResource(R.string.noCuenta)
                        else stringResource(R.string.siCuenta)
                    val accion =
                        if (showLoginForm.value) stringResource(R.string.registrate)
                        else stringResource(R.string.iniciaSesion)
                    Text(text = pregunta)
                    Text(
                        text = accion,
                        modifier = Modifier
                            .clickable { showLoginForm.value = !showLoginForm.value }
                            .padding(start = 5.dp),
                        color = Color.Blue
                    )
                }
            } else {
                Text(stringResource(R.string.crearCuenta))
                UserForm(isCreateAccount = true) { email, password ->
                    Log.d("Create", "Creo la cuenta $email y $password")
                    viewModel.createUserWithEmailAndPass(email, password) {
                        navController.navigate(ExploraCercaScreens.HomeScreen.name)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val pregunta =
                        if (showLoginForm.value) stringResource(R.string.noCuenta)
                        else stringResource(R.string.siCuenta)
                    val accion =
                        if (showLoginForm.value) stringResource(R.string.registrate)
                        else stringResource(R.string.iniciaSesion)
                    Text(text = pregunta)
                    Text(
                        text = accion,
                        modifier = Modifier
                            .clickable { showLoginForm.value = !showLoginForm.value }
                            .padding(start = 5.dp),
                        color = Color.Blue
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->}
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)

    }
    //funcion que hace que si los valores de usuario o contraseña estan vacios
    //desactiva el boton para poder logarse o crear usuarios
    val valido = remember(email.value, password.value){
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty()
    }
    // Al dar click en el boton de logarse o crear el teclado se oculta
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        EmailInput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = stringResource(R.string.clave),
            passwordVisible = passwordVisible
        )
        SubmintButton(
            textId =
            if (isCreateAccount) stringResource(R.string.crearCuenta)
            else stringResource(R.string.iniciaSesion),
            inputValido = valido
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmintButton(
    textId: String,
    inputValido: Boolean,
    onClick: () ->Unit
) {
    Button(
        //Este onclick no lleva llaves porque es una función declarada arriba
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
    ){
        Text(
            text = textId,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId : String = stringResource(R.string.email)
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    //Si no queremos ver la contraseña el valor se mantiene en false y no la muestra
    //Si queremos ver la contraseña el valor cambia y se muestra
    //Si la queremos ocultar despues de verla se vuelve a ocultar
    val visualTransformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = {Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

