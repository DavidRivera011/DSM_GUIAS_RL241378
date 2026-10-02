package com.example.jetpackcomposeapp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetpackcomposeapp.navigation.AppNavDisplay
@Composable
fun ComposeCarrerasApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // safeDrawingPadding evita que el contenido quede bajo las barras del sistema
                    AppNavDisplay(modifier = Modifier.safeDrawingPadding())
        }
    }
}