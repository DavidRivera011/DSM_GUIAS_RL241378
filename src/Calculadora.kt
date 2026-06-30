import kotlin.math.sqrt

fun main() {
    var opcion: Int
    do {


        println("")
        println("----- MENU -----")
        println("1. Sumar")
        println("2. Restar")
        println("3. Multiplicacion")
        println("4. Division")
        println("5. Raiz de un numero")
        println("6. Salir")
        print("Elija una opcion: ")
        opcion = readln().toInt()
        println()

        var num1: Double = 0.0
        var num2: Double = 0.0

        when (opcion) {
            1 -> {
                print("Primer Numero: ")
                num1 = readln().toDouble()
                print("Segundo Numero: ")
                num2 = readln().toDouble()

                println("Resultado: ${obtenerSuma(num1, num2)}")

                if (!devolver()) {
                    println("Gracias por usar la calculadora")
                    return
                }
            }
            2 -> {
                print("Primer Numero: ")
                num1 = readln().toDouble()
                print("Segundo Numero: ")
                num2 = readln().toDouble()

                println("Resultado: ${obtenerResta(num1, num2)}")

                if (!devolver()) {
                    println("Gracias por usar la calculadora")
                    return
                }
            }
            3 -> {
                print("Primer Numero: ")
                num1 = readln().toDouble()
                print("Segundo Numero: ")
                num2 = readln().toDouble()

                println("Resultado: ${obtenerMultiplicacion(num1, num2)}")

                if (!devolver()) {
                    println("Gracias por usar la calculadora")
                    return
                }
            }
            4 -> {
                print("Primer Numero: ")
                num1 = readln().toDouble()
                print("Segundo Numero: ")
                num2 = readln().toDouble()

                println("Resultado: ${obtenerDivision(num1, num2)}")

                if (!devolver()) {
                    println("Gracias por usar la calculadora")
                    return
                }
            }
            5 -> {
                print("Raiz de: ")
                num1 = readln().toDouble()

                println("Resultado: ${obtenerRaiz(num1)}")

                if (!devolver()) {
                    println("Gracias por usar la calculadora")
                    return
                }
            }
            6 -> {
                println("Gracias por usar la calculadora")
                println("Tenga un buen dia")

                return
            }
            else -> println("Opcion no valida")
        }
    } while (true)
}

fun devolver(): Boolean {
    while (true) {
        print("Deseas realizar otra operación (S/N): ")
        when (readln().uppercase()) {
            "S" -> return true
            "N" -> return false
            else -> println("Opción no válida.")
        }
    }
}


fun obtenerSuma(num1: Double, num2: Double): Double {
    val respuesta = num1 + num2
    return respuesta
}
fun obtenerResta(num1: Double, num2: Double): Double {
    val respuesta = num1 - num2
    return respuesta
}
fun obtenerMultiplicacion(num1: Double, num2: Double): Double {
    val respuesta = num1 * num2
    return respuesta
}
fun obtenerDivision(num1: Double, num2: Double): Double {
    val respuesta = num1 / num2
    return respuesta
}
fun obtenerRaiz(num1: Double): Double {
    val respuesta = sqrt(num1)
    return respuesta
}


