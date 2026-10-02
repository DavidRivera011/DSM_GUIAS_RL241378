package com.example.guia9.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.guia9.db.HelperDB

class Usuario(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        const val TABLE_NAME_USUARIO = "usuario"

        const val COL_ID = "idusuario"
        const val COL_NICK = "nick"
        const val COL_PASSWORD = "password"

        const val CREATE_TABLE_USUARIO =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_USUARIO (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NICK VARCHAR(50) NOT NULL UNIQUE," +
                    "$COL_PASSWORD VARCHAR(100) NOT NULL" +
                    ");"
    }

    fun generarContentValues(
        nick: String?,
        password: String?
    ): ContentValues {
        val valores = ContentValues()

        valores.put(COL_NICK, nick)
        valores.put(COL_PASSWORD, password)

        return valores
    }

    fun registrarUsuario(
        nick: String,
        password: String
    ): Long {
        return db!!.insert(
            TABLE_NAME_USUARIO,
            null,
            generarContentValues(nick, password)
        )
    }

    fun validarUsuario(
        nick: String,
        password: String
    ): Cursor? {

        val columns = arrayOf(
            COL_ID,
            COL_NICK,
            COL_PASSWORD
        )

        return db!!.query(
            TABLE_NAME_USUARIO,
            columns,
            "$COL_NICK=? AND $COL_PASSWORD=?",
            arrayOf(nick, password),
            null,
            null,
            null
        )
    }

    fun existeNick(nick: String): Boolean {

        val cursor = db!!.query(
            TABLE_NAME_USUARIO,
            arrayOf(COL_ID),
            "$COL_NICK=?",
            arrayOf(nick),
            null,
            null,
            null
        )

        val existe = cursor.moveToFirst()

        cursor.close()

        return existe
    }
}