package com.example.jetpackcomposeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.model.Materia
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.jetpackcomposeapp.R

@Composable
fun MateriaDetailScreen(
    materia: Materia,
    esFavorita: Boolean,
    onFavoritoClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text = materia.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = onFavoritoClick
            ) {

                Icon(
                    imageVector =
                        if (esFavorita)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,

                    contentDescription =
                        if (esFavorita)
                            "Quitar de favoritos"
                        else
                            "Agregar a favoritos"
                )
            }
        }

        Image(
            painter = painterResource(
                id = when (materia.codigo) {
                    "SIS101" -> R.drawable.programacion
                    "SIS201" -> R.drawable.estructuras
                    "MEC101" -> R.drawable.matematicas
                    else -> R.drawable.programacion
                }
            ),
            contentDescription = materia.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Código: ${materia.codigo}"
        )

        Text(
            text = "UVs: ${materia.uvs}"
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Descripción:",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = materia.descripcion,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Temas principales:",
            style = MaterialTheme.typography.titleMedium
        )

        materia.temas.forEach { tema ->

            Row(
                modifier = Modifier.padding(
                    vertical = 4.dp
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(text = tema)
            }
        }
    }
}