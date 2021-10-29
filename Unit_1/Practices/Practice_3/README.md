# Practice 2

# Exercise 1
``` scala
//1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
var lista: List[String] = List( "rojo", "blanco", "negro")
```
# Exercise 2
``` scala
//2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
var suma1 = lista ::: List("verde" ,"amarillo", "azul", "naranja", "perla")
```
# Exercise 3
``` scala
//3. Traer los elementos de "lista" "verde", "amarillo", "azul"
suma1.slice(3,6)
```
# Exercise 4
``` scala
//4. Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
val arr = Array.range(0, 1000,5)
```
# Exercise 5
``` scala
//5. Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos
var lista = Lista(1,3,3,4,6,7,3,7)
lista.toSet
```
# Exercise 6
``` scala
//6. Crea una mapa mutable llamado nombres que contenga los siguiente
//     "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
//   6 a . Imprime todas la llaves del mapa
//   6 b . Agrega el siguiente valor al mapa("Miguel", 23)
val nombres = Map( ("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana", "27"))
nombres.keys
var suma1 = nombres + ("Miguel" -> 23)

```
