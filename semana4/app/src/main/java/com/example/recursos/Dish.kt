package com.example.recursos

data class Dish(
    val name: String,
    val description: String,
    val price: String,
    val imageName: String,
    val category: String,
    var isFavorite: Boolean = false
)
