package com.example.jetpackcomposeapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.jetpackcomposeapp.ui.screens.CarreraDetailScreen
import com.example.jetpackcomposeapp.ui.screens.CarrerasListScreen
import com.example.jetpackcomposeapp.ui.screens.MateriaDetailScreen
import com.example.jetpackcomposeapp.viewmodel.CarrerasViewModel
import kotlinx.serialization.Serializable
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
// Cada pantalla se identifica con una clave serializable
@Serializable
data object HomeKey : NavKey
@Serializable
data class CarreraKey(val nombre: String) : NavKey
@Serializable
data class MateriaKey(val codigo: String) : NavKey
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    viewModel: CarrerasViewModel = viewModel()
) {

    val backStack =
        rememberNavBackStack(HomeKey)

    val carreras by
    viewModel.carreras
        .collectAsStateWithLifecycle()

    val favoritos by
    viewModel.favoritos
        .collectAsStateWithLifecycle()

    val listDetailStrategy =
        rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        modifier = modifier,

        backStack = backStack,

        onBack = {
            backStack.removeLastOrNull()
        },

        sceneStrategies =
            listOf(listDetailStrategy),

        entryProvider = entryProvider {

            entry<HomeKey>(
                metadata =
                    ListDetailSceneStrategy.listPane()
            ) {

                CarrerasListScreen(
                    carreras = carreras,

                    onCarreraClick = { nombre ->

                        backStack.add(
                            CarreraKey(nombre)
                        )
                    }
                )
            }

            entry<CarreraKey>(
                metadata =
                    ListDetailSceneStrategy.listPane()
            ) { key ->

                CarreraDetailScreen(
                    carreraName = key.nombre,

                    materias =
                        carreras[key.nombre]
                            ?: emptyList(),

                    onMateriaClick = { codigo ->

                        backStack.add(
                            MateriaKey(codigo)
                        )
                    }
                )
            }

            entry<MateriaKey>(
                metadata =
                    ListDetailSceneStrategy.detailPane()
            ) { key ->

                val materia =
                    viewModel.buscarMateriaPorCodigo(
                        key.codigo
                    )

                if (materia != null) {

                    MateriaDetailScreen(
                        materia = materia,

                        esFavorita =
                            favoritos.contains(
                                materia.codigo
                            ),

                        onFavoritoClick = {
                            viewModel.cambiarFavorito(
                                materia.codigo
                            )
                        }
                    )

                } else {

                    Text(
                        text =
                            "Materia no encontrada"
                    )
                }
            }
        }
    )
}