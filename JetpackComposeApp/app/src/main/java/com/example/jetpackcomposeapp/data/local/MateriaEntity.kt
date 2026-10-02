package com.example.jetpackcomposeapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "materias",
    foreignKeys = [
        ForeignKey(
            entity = CarreraEntity::class,
            parentColumns = ["id"],
            childColumns = ["carreraId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MateriaEntity(

    @PrimaryKey
    val codigo: String,

    val nombre: String,

    val uvs: Int,

    val descripcion: String,

    val temas: String,

    val carreraId: Int
)