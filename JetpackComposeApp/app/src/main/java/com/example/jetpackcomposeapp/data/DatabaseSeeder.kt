package com.example.jetpackcomposeapp.data

import com.example.jetpackcomposeapp.data.local.CarreraEntity
import com.example.jetpackcomposeapp.data.local.MateriaDao
import com.example.jetpackcomposeapp.data.local.MateriaEntity

class DatabaseSeeder(
    private val dao: MateriaDao
) {

    suspend fun seed() {

        if (dao.cantidadCarreras() > 0) {
            return
        }

        sampleCarreras.forEach { (nombreCarrera, materias) ->

            val carreraId = dao.insertarCarrera(
                CarreraEntity(
                    nombre = nombreCarrera
                )
            ).toInt()

            val entidades = materias.map { materia ->

                MateriaEntity(
                    codigo = materia.codigo,
                    nombre = materia.nombre,
                    uvs = materia.uvs,
                    descripcion = materia.descripcion,
                    temas = materia.temas.joinToString("|"),
                    carreraId = carreraId
                )
            }

            dao.insertarMaterias(entidades)
        }
    }
}