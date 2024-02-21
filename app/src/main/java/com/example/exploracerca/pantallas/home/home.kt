package com.example.exploracerca.pantallas.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.exploracerca.R
import com.example.exploracerca.modelo.Restaurantes
import com.example.exploracerca.navegacion.ExploraCercaScreens
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController
){
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (

        topBar = {
            RestaurantesAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackPressed = { viewModel.navigateToListPage() }
            )
        }
    ){innerPadding ->
        if (uiState.isShowingListPage){
            RestaurantesList(
                Restaurantes = uiState.RestaurantesList,
                onClick = {
                    viewModel.updateCurrentRestaurante(it)
                    viewModel.navigateToDetailPage()
                },
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                contentPadding = innerPadding,
            )
        }else{
            RestauranteDescription(
                selectedRestaurantes = uiState.currentRestaurante,
                contentPadding = innerPadding,
                onBackPressed = {
                    viewModel.navigateToListPage()
                },
                navController = navController
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantesAppBar(
    onBackPressed: () -> Unit,
    isShowingListPage: Boolean,
    modifier: Modifier = Modifier
){
    val isShowingDetailPage =  !isShowingListPage
    TopAppBar(
        title = {
            Text(
                text =
                if (isShowingDetailPage){
                    stringResource(R.string.Realizar_una_reserva)
                }else{
                    stringResource(R.string.Restaurantes)
                },
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = if (isShowingDetailPage){
            {
                IconButton(onClick = onBackPressed){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.volver)
                    )
                }
            }
        } else {
            { Box {} }
        },
        modifier = modifier,
    )
}





// Composable para mostrar la imagen de una actividad en la lista


// Composable para la lista de actividades
@Composable
private fun RestaurantesList(
    Restaurantes: List<Restaurantes>,
    onClick: (Restaurantes) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val (restaurantesPares, restaurantesImpares) = Restaurantes.partition { it.id % 2 == 0 }

    Row(modifier = modifier) {
        // LazyColumn para restaurantes con IDs pares
        Column(modifier = Modifier.weight(1f)) {
            LazyColumn(
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            ) {
                items(restaurantesPares, key = { it.id }) { Restaurante ->
                    RestaurantesListItem(
                        restaurantes = Restaurante,
                        onItemClick = onClick
                    )
                }
            }
        }

        // LazyColumn para restaurantes con IDs impares
        Column(modifier = Modifier.weight(1f)) {
            LazyColumn(
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            ) {
                items(restaurantesImpares, key = { it.id }) { Restaurante ->
                    RestaurantesListItem(
                        restaurantes = Restaurante,
                        onItemClick = onClick
                    )
                }
            }
        }
    }
}
// Restaurante creado para añadir a la lista
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantesListItem(
    restaurantes: Restaurantes,
    onItemClick: (Restaurantes) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        border = BorderStroke(2.dp, Color.Black),
        colors = cardColors(Color.Cyan),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(restaurantes) }

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagen del restaurante
            RestaurantesImageItem(
                restaurante = restaurantes,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.card_image_height))
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                // Nombre del restaurante
                Text(
                    text = stringResource(restaurantes.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                // Mesas que tiene el restaurante
                Row {
                    Text(
                        text = pluralStringResource(
                            R.plurals.mesas ,
                            restaurantes.Capacidad,
                            restaurantes.Capacidad
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

    }
}

@Composable
private fun RestaurantesImageItem(restaurante: Restaurantes, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(restaurante.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Descripciones de los restaurantes
@Composable
private fun RestauranteDescription(
    selectedRestaurantes: Restaurantes,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {

            Box {
                Box {
                    Image(
                        painter = painterResource(selectedRestaurantes.imageResourceId),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedRestaurantes.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    // Número de personas
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = pluralStringResource(
                                R.plurals.mesas,
                                selectedRestaurantes.Capacidad,
                                selectedRestaurantes.Capacidad),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
            }
            Box(modifier = Modifier.height(300.dp)) {
                Text(
                    text = stringResource(selectedRestaurantes.restauranteDetails),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                        horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                    )
                )
            }
            // Google maps enseñando la ubicación del restaurante seleccionado
            Box(modifier = Modifier.height(750.dp)){
                val marker = LatLng(selectedRestaurantes.latitud, selectedRestaurantes.longitud)
                val cameraPosition = rememberCameraPositionState{ position = CameraPosition.fromLatLngZoom(marker, 15f)}
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    properties = MapProperties(mapType = MapType.HYBRID),
                    cameraPositionState = cameraPosition,
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ){
                    Marker(position = marker, title = stringResource(selectedRestaurantes.titleResourceId))
                }
            }
            Box(modifier = Modifier.height(300.dp).padding(top = 10.dp)) {
                // Botón para hacer una reserva
                ReservaButton(selectedRestaurantes, viewModel(), navController)
            }
        }
    }
}

// funcion para crear el boton para la reserva y conseguir los datos necesarios
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaButton(
    selectedRestaurantes: Restaurantes,
    viewModel: HomeViewModel,
    navController: NavController,
) {
    var nomRestaurante = stringResource(selectedRestaurantes.titleResourceId)
    var numPersonas by remember { mutableStateOf(1) }
    var telefono by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }
    var Confirmacion by remember { mutableStateOf(false) }
    val limiteClnt = selectedRestaurantes.Capacidad

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        OutlinedTextField(
            value = numPersonas.toString(),
            onValueChange = { newValue ->
                val newNum = newValue.toIntOrNull() ?: 0
                numPersonas = when {
                    newNum < 0 -> 1
                    newNum > limiteClnt -> limiteClnt
                    else -> newNum
                }
            },
            label = { Text("Comensales (límite: $limiteClnt)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono de contacto") },
            modifier = Modifier.padding(8.dp)

        )

        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            value = fechaSeleccionada ?: "",
            onValueChange = { fechaSeleccionada = it },
            label = { Text("Fecha de reserva (DD-MM-yyyy)") },
            modifier = Modifier.padding(8.dp)
        )

        Button(
            onClick = {
                Confirmacion = true
            },
            content = { Text(stringResource(R.string.confirmarRes)) },
            modifier = Modifier.padding(8.dp)

        )
    }

    if (Confirmacion) {
        AlertDialog(
            onDismissRequest = {
                Confirmacion = false
            },
            title = { Text(stringResource(R.string.confirmarRes)) },
            text = { Text(stringResource(R.string.preguntaConfirmarRes)) },
            confirmButton = {
                Button(
                    onClick = {
                        if (fechaSeleccionada != null) {
                            viewModel.crearReservaFirestore(
                                nomRestaurante,
                                fechaSeleccionada!!,
                                numPersonas,
                                telefono
                            )
                        }
                        Confirmacion = false
                        navController.navigate(ExploraCercaScreens.HomeScreen.name)
                    }
                ) {
                    Text(stringResource(R.string.si))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Cancelar la reserva si se cancela la confirmación
                        Confirmacion = false // Ocultar el AlertDialog
                    }
                ) {
                    Text(stringResource(R.string.cancelar))
                }
            }
        )
    }
}
