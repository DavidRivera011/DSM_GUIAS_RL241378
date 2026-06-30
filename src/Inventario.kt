data class Producto(
    var nombre: String?,
    var cantidad: Int?,
    var precio: Double?,
)

class Inventario (var productos: ArrayList<Producto>){
    fun addProducto(producto: Producto){

        for(p in productos){
            if (p.nombre == producto.nombre ){
                println("Producto '${producto.nombre}' ya existe")
                return
            }
        }

        productos.add(producto)
        println("Producto agregado: ${producto.nombre}")
    }

    fun showProductos() {
        for (producto in productos) {
            println("| Nombre    | Cantidad   | Precio   ")
            println("| ${producto.nombre} | ${producto.cantidad} | ${producto.precio}")
        }
    }

    fun showProducto(nombre: String){
        for (producto in productos){
            if (producto.nombre == nombre){
                println("Nombre: ${producto.nombre}")
                println("Cantidad: ${producto.cantidad}")
                println("Precio: ${producto.precio}")

                return
            }


        }
        println("Producto no encontrado")
    }
}