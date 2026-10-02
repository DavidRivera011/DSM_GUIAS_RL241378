package com.example.jetpackcomposeapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore by preferencesDataStore(
    name = "favoritos"
)

class FavoritesDataStore(
    private val context: Context
) {

    companion object {
        private val FAVORITES_KEY =
            stringSetPreferencesKey("materias_favoritas")
    }

    val favoritos: Flow<Set<String>> =
        context.favoritesDataStore.data.map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun cambiarFavorito(codigo: String) {

        context.favoritesDataStore.edit { preferences ->

            val favoritosActuales =
                preferences[FAVORITES_KEY]?.toMutableSet()
                    ?: mutableSetOf()

            if (favoritosActuales.contains(codigo)) {
                favoritosActuales.remove(codigo)
            } else {
                favoritosActuales.add(codigo)
            }

            preferences[FAVORITES_KEY] = favoritosActuales
        }
    }
}