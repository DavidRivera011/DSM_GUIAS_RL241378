package com.example.jetpackcomposeapp.model

data class Materia(
    val nombre: String, // Nombre completo de la materia
    val uvs: Int, // Unidades valorativas
    val codigo: String, // Código único (ej: SIS101)
    val descripcion: String, // Descripción general
    val temas: List<String> // Lista de temas principales
)