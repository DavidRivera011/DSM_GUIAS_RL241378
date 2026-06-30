fun main(){
    do {
        println("Ingrese un numero para verificar si es par o impar.")
        print("Número: ")
        val num = readln().toDoubleOrNull()

        if (num == null){
            println("Carácter inválido. Debe ingresar un número entero.")
            println()
            continue
        }


        if (num % 2 == 0.0){
            println("----------------------")
            println("El numero es par")
            println("----------------------")
        }else{
            println("----------------------")
            println("El numero es impar")
            println("----------------------")
        }

        do {
            println("Desea comprobar otro número? (S/N)")
            val opcion = readln().uppercase()
            if (opcion == "S"){
                break
            }else if(opcion == "N"){
                println("----------------------")
                println("Tenga un buen dia")
                println("----------------------")
                return
            } else{
                println("----------------------")
                println("Caracter invalido.")
                println("----------------------")
                continue
            }
        }while (true)

    }while (true)
}