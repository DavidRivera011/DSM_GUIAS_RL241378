package com.example.jetpackcomposeapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.jetpackcomposeapp.model.Materia
import com.example.jetpackcomposeapp.ui.screens.CarrerasListScreen
import org.junit.Rule
import org.junit.Test

class CarrerasListScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    private val carreras =
        mapOf(
            "Ingeniería en Sistemas" to listOf(
                Materia(
                    nombre = "Programación I",
                    uvs = 4,
                    codigo = "SIS101",
                    descripcion =
                        "Introducción a la programación",
                    temas = listOf(
                        "Variables",
                        "Condicionales",
                        "Funciones"
                    )
                )
            ),
            "Ingeniería en Mecatrónica" to listOf(
                Materia(
                    nombre = "Matemáticas I",
                    uvs = 5,
                    codigo = "MEC101",
                    descripcion =
                        "Álgebra y cálculo",
                    temas = listOf(
                        "Límites",
                        "Derivadas"
                    )
                )
            )
        )

    @Test
    fun muestraLasCarreras() {

        composeTestRule.setContent {

            CarrerasListScreen(
                carreras = carreras,
                onCarreraClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                "Ingeniería en Sistemas"
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Ingeniería en Mecatrónica"
            )
            .assertIsDisplayed()
    }

    @Test
    fun permiteBuscarCarrera() {

        composeTestRule.setContent {

            CarrerasListScreen(
                carreras = carreras,
                onCarreraClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                "Buscar carrera"
            )
            .performTextInput("Sistemas")

        composeTestRule
            .onNodeWithText(
                "Ingeniería en Sistemas"
            )
            .assertIsDisplayed()
    }

    @Test
    fun permiteSeleccionarCarrera() {

        var carreraSeleccionada = ""

        composeTestRule.setContent {

            CarrerasListScreen(
                carreras = carreras,
                onCarreraClick = {
                    carreraSeleccionada = it
                }
            )
        }

        composeTestRule
            .onNodeWithText(
                "Ingeniería en Sistemas"
            )
            .performClick()

        assert(
            carreraSeleccionada ==
                    "Ingeniería en Sistemas"
        )
    }
}