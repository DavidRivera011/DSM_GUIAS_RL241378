fun main(){

    val inventario = Inventario(ArrayList())

    do {

        println("---------------------------")
        println("|     INVENTARIO JULIO    |")
        println("---------------------------")
        println()
        println("1 - Añadir producto")
        println("2 - Listar productos")
        println("3 - Buscar producto")
        println("4 - Salir")
        println("---------------------------")

        print("Seleccione una opción: ")
        val opcion1 = readln().toIntOrNull()
        println()

        when (opcion1) {
            1 -> {
                println("---------------------------")

                print("Ingrese nombre del producto: ")
                val nombre = readln()

                print("Ingrese cantidad: ")
                val cantidad = readln().toIntOrNull()

                if (cantidad == null) {
                    println("Cantidad inválida. Debe ingresar un número entero.")
                    println("Enter para continuar")
                    readln()
                    continue
                }

                print("Ingrese precio: ")
                val precio = readln().toDoubleOrNull()

                if (precio == null) {
                    println("Precio inválido. Debe ingresar un número.")
                    println("Enter para continuar")
                    readln()
                    continue
                }

                println("---------------------------")
                val producto = Producto(nombre, cantidad, precio)
                inventario.addProducto(producto)
                println("---------------------------")
                println("Enter para continuar")
                readln()
            }

            2 -> {
                println("---------------------------")
                println("Productos:")
                inventario.showProductos()
                println("---------------------------")
                println("Enter para continuar")
                readln()
            }

            3 -> {
                println("---------------------------")
                print("Ingrese nombre del producto: ")
                val nombre = readln()

                println("Producto:")
                inventario.showProducto(nombre)
                println("---------------------------")
                println("Enter para continuar")
                readln()
            }

            4 -> return

            else -> {
                println("Opción inválida.")
                println("Enter para continuar")
                readln()
            }
        }

    }while (true)


}