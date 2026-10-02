package com.example.guia9

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.guia9.db.HelperDB
import com.example.guia9.model.Categoria
import com.example.guia9.model.Productos

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var managerCategoria: Categoria? = null
    private var managerProductos: Productos? = null

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtNombre: EditText? = null
    private var txtPrecio: EditText? = null
    private var txtCantidad: EditText? = null
    private var cmbCategorias: Spinner? = null

    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Referencias de los controles
        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)
        txtNombre = findViewById(R.id.txtNombre)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtCantidad = findViewById(R.id.txtCantidad)
        cmbCategorias = findViewById(R.id.cmbCategorias)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)

        // Inicializar base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        // Cargar categorías
        setSpinnerCategorias()

        // Eventos de botones
        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)
    }

    /**
     * Carga las categorías existentes en el Spinner.
     */
    private fun setSpinnerCategorias() {

        managerCategoria = Categoria(this)

        // Insertar valores por defecto
        managerCategoria!!.insertValuesDefault()

        // Obtener categorías
        cursor = managerCategoria!!.showAllCategoria()

        val cat = ArrayList<String>()

        if (cursor != null && cursor!!.count > 0) {

            cursor!!.moveToFirst()

            do {
                cat.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }

        // Adaptador del Spinner
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cat
        )

        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        cmbCategorias!!.adapter = adaptador
    }

    /**
     * Maneja los botones de la pantalla.
     */
    override fun onClick(view: View) {

        managerProductos = Productos(this)

        val nombre = txtNombre!!.text.toString().trim()
        val precio = txtPrecio!!.text.toString().trim()
        val cantidad = txtCantidad!!.text.toString().trim()
        val idproducto = txtId!!.text.toString().trim()
        val idproductoTemp = txtIdDB!!.text.toString().trim()

        var idcategoria = 0

        // Obtener categoría seleccionada
        if (cmbCategorias!!.selectedItem != null) {

            val categoria =
                cmbCategorias!!.selectedItem.toString().trim()

            // searchID devuelve Int?, por eso usamos ?: 0
            idcategoria =
                managerCategoria!!.searchID(categoria) ?: 0
        }

        if (db == null) {

            Toast.makeText(
                this,
                "No se puede conectar a la Base de Datos",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        // =========================================================
        // AGREGAR
        // =========================================================
        if (view === btnAgregar) {

            if (verificarFormulario("insertar")) {

                try {

                    managerProductos!!.addNewProducto(
                        idcategoria,
                        nombre,
                        precio.toDouble(),
                        cantidad.toInt()
                    )

                    Toast.makeText(
                        this,
                        "Producto agregado",
                        Toast.LENGTH_LONG
                    ).show()

                    limpiarFormulario()

                } catch (e: Exception) {

                    Toast.makeText(
                        this,
                        "Error al agregar el producto",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.e(
                        "ERROR_PRODUCTO",
                        "Error al agregar producto",
                        e
                    )
                }
            }
        }

        // =========================================================
        // ACTUALIZAR
        // =========================================================
        else if (view === btnActualizar) {

            if (verificarFormulario("actualizar")) {

                try {

                    cursor = managerProductos!!.searchProducto(
                        idproductoTemp.toInt()
                    )

                    if (cursor != null && cursor!!.count > 0) {

                        managerProductos!!.updateProducto(
                            idproductoTemp.toInt(),
                            idcategoria,
                            nombre,
                            precio.toDouble(),
                            cantidad.toInt()
                        )

                        Toast.makeText(
                            this,
                            "Producto actualizado correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {

                        Toast.makeText(
                            this,
                            "No se ha encontrado el producto cargado",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {

                    Toast.makeText(
                        this,
                        "Error al actualizar el producto",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.e(
                        "ERROR_PRODUCTO",
                        "Error al actualizar producto",
                        e
                    )
                }
            }
        }

        // =========================================================
        // ELIMINAR
        // =========================================================
        else if (view === btnEliminar) {

            if (verificarFormulario("eliminar")) {

                try {

                    cursor = managerProductos!!.searchProducto(
                        idproductoTemp.toInt()
                    )

                    if (cursor != null && cursor!!.count > 0) {

                        AlertDialog.Builder(this)
                            .setTitle("Eliminar producto")
                            .setMessage(
                                "¿Desea eliminar el producto \"$nombre\"?"
                            )
                            .setCancelable(false)
                            .setPositiveButton("Sí") { _, _ ->

                                try {

                                    managerProductos!!.deleteProducto(
                                        idproductoTemp.toInt()
                                    )

                                    Toast.makeText(
                                        this,
                                        "Producto eliminado",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    limpiarFormulario()

                                } catch (e: Exception) {

                                    Toast.makeText(
                                        this,
                                        "Error al eliminar el producto",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    Log.e(
                                        "ERROR_PRODUCTO",
                                        "Error al eliminar producto",
                                        e
                                    )
                                }
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()

                    } else {

                        Toast.makeText(
                            this,
                            "No se ha encontrado el producto cargado",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {

                    Toast.makeText(
                        this,
                        "Error al buscar el producto",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.e(
                        "ERROR_PRODUCTO",
                        "Error al comprobar producto",
                        e
                    )
                }
            }
        }

        // =========================================================
        // BUSCAR
        // =========================================================
        else if (view === btnBuscar) {

            if (verificarFormulario("buscar")) {

                try {

                    cursor = managerProductos!!.searchProducto(
                        idproducto.toInt()
                    )

                    if (cursor != null && cursor!!.count > 0) {

                        cursor!!.moveToFirst()

                        // Guardar el ID real encontrado
                        txtIdDB!!.text =
                            cursor!!.getInt(0).toString()

                        // Obtener ID de categoría
                        val idCategoriaEncontrada =
                            cursor!!.getInt(1)

                        // Obtener nombre de categoría
                        val nombreCategoria =
                            managerCategoria!!.searchNombre(
                                idCategoriaEncontrada
                            )

                        // Seleccionar la categoría encontrada
                        if (nombreCategoria != null) {

                            val adapter =
                                cmbCategorias!!.adapter

                            for (i in 0 until adapter.count) {

                                if (
                                    adapter.getItem(i)
                                        .toString()
                                        .equals(
                                            nombreCategoria,
                                            ignoreCase = true
                                        )
                                ) {

                                    cmbCategorias!!.setSelection(i)
                                    break
                                }
                            }
                        }

                        // Cargar datos del producto
                        txtNombre!!.setText(
                            cursor!!.getString(2)
                        )

                        txtPrecio!!.setText(
                            cursor!!.getString(3)
                        )

                        txtCantidad!!.setText(
                            cursor!!.getString(4)
                        )

                        Log.d(
                            "INFORMACION",
                            "Producto encontrado: ${cursor!!.getInt(0)}"
                        )

                        Toast.makeText(
                            this,
                            "Producto encontrado",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {

                        // No existe el producto
                        txtIdDB!!.text = ""

                        Toast.makeText(
                            this,
                            "No se encontraron registros",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {

                    Toast.makeText(
                        this,
                        "Error al buscar el producto",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.e(
                        "ERROR_BUSCAR",
                        "Error al buscar producto",
                        e
                    )
                }
            }
        }
    }

    /**
     * Valida los datos antes de realizar una operación.
     */
    private fun verificarFormulario(opc: String): Boolean {

        var notificacion =
            "Se han generado algunos errores, favor verifíquelos"

        var response = true

        val nombre =
            txtNombre!!.text.toString().trim()

        val precio =
            txtPrecio!!.text.toString().trim()

        val cantidad =
            txtCantidad!!.text.toString().trim()

        val idproducto =
            txtId!!.text.toString().trim()

        val idproductoTemp =
            txtIdDB!!.text.toString().trim()

        // =========================================================
        // INSERTAR / ACTUALIZAR
        // =========================================================
        if (opc == "insertar" || opc == "actualizar") {

            if (nombre.isEmpty()) {

                txtNombre!!.error =
                    "Ingrese el nombre del producto"

                txtNombre!!.requestFocus()

                response = false
            }

            if (precio.isEmpty()) {

                txtPrecio!!.error =
                    "Ingrese el precio del producto"

                if (response) {
                    txtPrecio!!.requestFocus()
                }

                response = false

            } else {

                try {

                    if (precio.toDouble() < 0) {

                        txtPrecio!!.error =
                            "El precio no puede ser negativo"

                        response = false
                    }

                } catch (e: NumberFormatException) {

                    txtPrecio!!.error =
                        "Ingrese un precio válido"

                    response = false
                }
            }

            if (cantidad.isEmpty()) {

                txtCantidad!!.error =
                    "Ingrese la cantidad inicial"

                if (response) {
                    txtCantidad!!.requestFocus()
                }

                response = false

            } else {

                try {

                    if (cantidad.toInt() < 0) {

                        txtCantidad!!.error =
                            "La cantidad no puede ser negativa"

                        response = false
                    }

                } catch (e: NumberFormatException) {

                    txtCantidad!!.error =
                        "Ingrese una cantidad válida"

                    response = false
                }
            }

            // Para actualizar necesitamos un producto previamente buscado
            if (opc == "actualizar") {

                if (idproductoTemp.isEmpty()) {

                    response = false

                    notificacion =
                        "Primero debe buscar un producto para actualizar"
                }
            }
        }

        // =========================================================
        // ELIMINAR
        // =========================================================
        else if (opc == "eliminar") {

            if (idproductoTemp.isEmpty()) {

                response = false

                notificacion =
                    "Primero debe buscar un producto para eliminar"
            }
        }

        // =========================================================
        // BUSCAR
        // =========================================================
        else if (opc == "buscar") {

            if (idproducto.isEmpty()) {

                response = false

                notificacion =
                    "Ingrese el código del producto que desea buscar"

                txtId!!.error =
                    "Ingrese el código"

                txtId!!.requestFocus()

            } else {

                try {

                    if (idproducto.toInt() <= 0) {

                        response = false

                        notificacion =
                            "Ingrese un código de producto válido"

                        txtId!!.error =
                            "Código inválido"

                        txtId!!.requestFocus()
                    }

                } catch (e: NumberFormatException) {

                    response = false

                    notificacion =
                        "El código debe ser numérico"

                    txtId!!.error =
                        "Ingrese solamente números"

                    txtId!!.requestFocus()
                }
            }
        }

        // =========================================================
        // MOSTRAR ERRORES
        // =========================================================
        if (!response) {

            Toast.makeText(
                this,
                notificacion,
                Toast.LENGTH_LONG
            ).show()
        }

        return response
    }

    /**
     * Limpia los campos del formulario.
     */
    private fun limpiarFormulario() {

        txtIdDB!!.text = ""

        txtId!!.setText("")
        txtNombre!!.setText("")
        txtPrecio!!.setText("")
        txtCantidad!!.setText("")

        if (cmbCategorias!!.adapter != null &&
            cmbCategorias!!.adapter.count > 0
        ) {
            cmbCategorias!!.setSelection(0)
        }

        txtId!!.clearFocus()
        txtNombre!!.clearFocus()
        txtPrecio!!.clearFocus()
        txtCantidad!!.clearFocus()
    }

    override fun onDestroy() {
        super.onDestroy()

        cursor?.close()
        db?.close()
        dbHelper?.close()
    }
}