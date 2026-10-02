package com.example.jetpackcomposeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeapp.model.Materia
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarreraDetailScreen(
    carreraName: String,
    materias: List<Materia>,
    onMateriaClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = carreraName) })
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(materias) { materia ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onMateriaClick(materia.codigo) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = materia.nombre, style =
                            MaterialTheme.typography.headlineMedium)
                        Text(
                            text = "${materia.uvs} UVs • ${materia.codigo}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Ver")
                }
                HorizontalDivider()
            }
        }
    }
}