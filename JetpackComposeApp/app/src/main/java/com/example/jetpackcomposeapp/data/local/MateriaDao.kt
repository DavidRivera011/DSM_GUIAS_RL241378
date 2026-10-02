package com.example.jetpackcomposeapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class MateriaConCarrera(
    val codigo: String,
    val nombre: String,
    val uvs: Int,
    val descripcion: String,
    val temas: String,
    val carreraNombre: String
)

@Dao
interface MateriaDao {

    @Query(
        """
        SELECT 
            m.codigo,
            m.nombre,
            m.uvs,
            m.descripcion,
            m.temas,
            c.nombre AS carreraNombre
        FROM materias m
        INNER JOIN carreras c
        ON m.carreraId = c.id
        ORDER BY c.nombre, m.nombre
        """
    )
    fun observarMaterias(): Flow<List<MateriaConCarrera>>

    @Query("SELECT COUNT(*) FROM carreras")
    suspend fun cantidadCarreras(): Int

    @Query("SELECT id FROM carreras WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerIdCarrera(nombre: String): Int?

    @Insert
    suspend fun insertarCarrera(carrera: CarreraEntity): Long

    @Insert
    suspend fun insertarMateria(materia: MateriaEntity)

    @Insert
    suspend fun insertarMaterias(
        materias: List<MateriaEntity>
    )

    @Query("DELETE FROM materias WHERE codigo = :codigo")
    suspend fun eliminarMateria(codigo: String)

    @Query(
        """
        UPDATE materias
        SET nombre = :nombre,
            uvs = :uvs,
            descripcion = :descripcion,
            temas = :temas
        WHERE codigo = :codigo
        """
    )
    suspend fun actualizarMateria(
        codigo: String,
        nombre: String,
        uvs: Int,
        descripcion: String,
        temas: String
    )
}