# Practica 4

# Exercise 1
``` scala
//1. Algoritmo 1 Versión recursiva descendente
def fib(num: Int):Int ={
  if(num < 2){
    return num
  }else{
  return fib(num-1)+fib(num-2)
  }
}
```
# Exercise 2
``` scala
//2. Algoritmo 2 Versión con fórmula explícita
import scala.math.sqrt
import scala.math.pow
def fib2(num: Double):Double ={
  var j = 0.0
  if(num < 2){
    return num
  }else{
    val aureo = (1+sqrt(5))/2
    j = pow(aureo,num)
    j = j - pow((1.0 - aureo),num)
    j = j / sqrt(5)

  }
  return j
}
```
# Exercise 3
``` scala
//3. Algoritmo 3 Versión iterativa
def fib3(num: Int):Int ={
  var a = 0
  var b = 1
  var c = 0
  for(k <- 0 until num){
    c = b + a
    a = b
    b = c
  }
  return a
}
```
# Exercise 4
``` scala
//4. Algoritmo 4 Versión iterativa 2 variables
def fib4(num: Int):Int ={
  var a = 0
  var b = 1
  for(k <- 0 until num){
    b=b+a
    a=b-a
  }
  return b
}
```
# Exercise 5
``` scala
//5. Algoritmo 5 Versión iterativa vector
def fib5(num: Int):Int ={
  if(num<2){
    return num
  }else{
    var vec = Array(0,1)
    for(k <- 2 until num+1){
        vec=vec:+(vec(k-1)+vec(k-2))
    }
    return vec(num)
  }
}
```
