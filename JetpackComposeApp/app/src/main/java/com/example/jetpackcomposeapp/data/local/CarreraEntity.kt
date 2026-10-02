package com.example.jetpackcomposeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carreras")
data class CarreraEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String
)