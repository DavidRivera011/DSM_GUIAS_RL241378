package com.example.jetpackcomposeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.model.Materia

@Composable
fun MateriaCrudScreen(
    materias: List<Materia>,
    onAgregar: (Materia) -> Unit,
    onActualizar: (Materia) -> Unit,
    onEliminar: (String) -> Unit
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var codigo by remember {
        mutableStateOf("")
    }

    var uvs by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    var temas by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Administrar materias",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = {
                codigo = it
            },
            label = {
                Text("Código")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uvs,
            onValueChange = {
                uvs = it
            },
            label = {
                Text("UVs")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = temas,
            onValueChange = {
                temas = it
            },
            label = {
                Text("Temas separados por coma")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {

                val materia = Materia(
                    nombre = nombre,
                    uvs = uvs.toIntOrNull() ?: 0,
                    codigo = codigo,
                    descripcion = descripcion,
                    temas = temas
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                )

                onAgregar(materia)

                nombre = ""
                codigo = ""
                uvs = ""
                descripcion = ""
                temas = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Agregar materia")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn {

            items(materias) { materia ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = materia.nombre,
                            style =
                                MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text =
                                "${materia.codigo} • ${materia.uvs} UVs"
                        )

                        Row {

                            TextButton(
                                onClick = {
                                    onActualizar(materia)
                                }
                            ) {
                                Text("Actualizar")
                            }

                            TextButton(
                                onClick = {
                                    onEliminar(
                                        materia.codigo
                                    )
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}