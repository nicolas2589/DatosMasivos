
//1. Desarrollar un algoritmo en scala que calcule el radio de un circulo

val diametro : Double = 5
val radio = diametro / 2
radio

//2. Desarrollar un algoritmo en scala que me diga si un numero es primo
val numero : Int = 7
var primo : Boolean = true

for(i <- Range(2, numero)) {
  if((numero % i) == 0) {
    primo = false
  }
}
if(primo){
  println("Es Primo")
} else {
  println("No es Primo")
}

//3. Dada la variable bird = "tweet", utiliza interpolacion de string para
//imprimir "Estoy ecribiendo un tweet"

var bird = "tweet"
printf("Estoy escribiendo un %s", bird)

//4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para
// extraer la secuencia "Luke"
var mensaje = "Hola Luke yo soy tu padre!"
mensaje.slice(5,9)

//5. Cual es la diferencia entre value y una variable en scala?
//Val es un valor especificado que no puede ser cambiado en el programa,
//por lo cual es llamado inmutable.
//Var es un valor que puede no ser especificado en el programa,
//y puede ser sujero de cambios en el transcurso del programa,
// por lo cual es llamado mutable.

//6. Dada la tupla (2,4,5,1,2,3,3.1416,23) regresa el numero 3.1416
val tupla = (2,4,5,1,2,3,3.1416,23)
tupla._7
