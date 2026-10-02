package com.example.jetpackcomposeapp.data

import com.example.jetpackcomposeapp.model.Materia

val sampleCarreras: Map<String, List<Materia>> = mapOf(
    "Ingeniería en Sistemas" to listOf(
        Materia(
            "Programación I", 4, "SIS101",
            "Introducción a la programación con Kotlin.",
            listOf("Variables", "Condicionales", "Funciones")
        ),
        Materia(
            "Estructuras de Datos", 4, "SIS201",
            "Estructuras básicas y análisis.",
            listOf("Listas", "Pilas", "Colas")
        )
    ),
    "Ingeniería en Mecatrónica" to listOf(
        Materia(
            "Matemáticas I", 5, "MEC101",
            "Álgebra y cálculo diferencial.",
            listOf("Límites", "Derivadas")
        )
    )
)