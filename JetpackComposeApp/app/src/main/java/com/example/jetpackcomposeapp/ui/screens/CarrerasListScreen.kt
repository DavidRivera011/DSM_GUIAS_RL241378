package com.example.jetpackcomposeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.model.Materia

@Composable
fun CarrerasListScreen(
    carreras: Map<String, List<Materia>>,
    onCarreraClick: (String) -> Unit
) {
    val searchState = rememberTextFieldState()

    val carrerasFiltradas = remember(
        carreras,
        searchState.text.toString()
    ) {
        val texto = searchState.text.toString().trim()

        if (texto.isEmpty()) {
            carreras
        } else {
            carreras.filterKeys {
                it.contains(texto, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Carreras",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            state = searchState,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Buscar carrera")
            },
            placeholder = {
                Text("Ej. Ingeniería en Sistemas")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (carrerasFiltradas.isEmpty()) {

            Text(
                text = "No se encontraron carreras",
                style = MaterialTheme.typography.bodyLarge
            )

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    carrerasFiltradas.keys.toList()
                ) { carreraName ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                onCarreraClick(carreraName)
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = carreraName,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "${carrerasFiltradas[carreraName]?.size ?: 0} materias",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}