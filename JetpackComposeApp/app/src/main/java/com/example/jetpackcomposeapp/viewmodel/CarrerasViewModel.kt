package com.example.jetpackcomposeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeapp.data.DatabaseSeeder
import com.example.jetpackcomposeapp.data.FavoritesDataStore
import com.example.jetpackcomposeapp.data.local.AppDatabase
import com.example.jetpackcomposeapp.data.local.MateriaEntity
import com.example.jetpackcomposeapp.model.Materia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CarrerasViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        AppDatabase.getDatabase(application)

    private val dao =
        database.materiaDao()

    private val favorites =
        FavoritesDataStore(application)

    private val _carreras =
        MutableStateFlow<Map<String, List<Materia>>>(emptyMap())

    val carreras: StateFlow<Map<String, List<Materia>>> =
        _carreras.asStateFlow()

    private val _favoritos =
        MutableStateFlow<Set<String>>(emptySet())

    val favoritos: StateFlow<Set<String>> =
        _favoritos.asStateFlow()

    init {

        viewModelScope.launch {

            DatabaseSeeder(dao).seed()

            dao.observarMaterias()
                .collect { lista ->

                    _carreras.value =
                        lista.groupBy {
                            it.carreraNombre
                        }.mapValues { (_, materias) ->

                            materias.map {

                                Materia(
                                    nombre = it.nombre,
                                    uvs = it.uvs,
                                    codigo = it.codigo,
                                    descripcion = it.descripcion,
                                    temas = it.temas
                                        .split("|")
                                )
                            }
                        }
                }
        }

        viewModelScope.launch {

            favorites.favoritos.collect {
                _favoritos.value = it
            }
        }
    }

    fun buscarMateriaPorCodigo(
        codigo: String
    ): Materia? {

        return _carreras.value.values
            .flatten()
            .firstOrNull {
                it.codigo == codigo
            }
    }

    fun cambiarFavorito(codigo: String) {

        viewModelScope.launch {
            favorites.cambiarFavorito(codigo)
        }
    }

    fun agregarMateria(
        carrera: String,
        materia: Materia
    ) {

        viewModelScope.launch {

            var carreraId =
                dao.obtenerIdCarrera(carrera)

            if (carreraId == null) {

                carreraId =
                    dao.insertarCarrera(
                        com.example.jetpackcomposeapp.data.local.CarreraEntity(
                            nombre = carrera
                        )
                    ).toInt()
            }

            dao.insertarMateria(
                MateriaEntity(
                    codigo = materia.codigo,
                    nombre = materia.nombre,
                    uvs = materia.uvs,
                    descripcion = materia.descripcion,
                    temas = materia.temas.joinToString("|"),
                    carreraId = carreraId
                )
            )
        }
    }

    fun actualizarMateria(
        materia: Materia
    ) {

        viewModelScope.launch {

            dao.actualizarMateria(
                codigo = materia.codigo,
                nombre = materia.nombre,
                uvs = materia.uvs,
                descripcion = materia.descripcion,
                temas = materia.temas.joinToString("|")
            )
        }
    }

    fun eliminarMateria(
        codigo: String
    ) {

        viewModelScope.launch {

            dao.eliminarMateria(codigo)
        }
    }
}