 # Practica 1

## Libraries
``` scala
//1.Se importan las librerías para poder trabajar con los archivos y herramientas
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}
```

## Load Dataset
``` scala
//2.Load the data stored 
val dataset = spark.read.option("header", "true").option("inferSchema","true")csv("data.csv")
```

## Get desired data 
``` scala
//3.Get desired data from the dataset 
val feature_data = dataset.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper","Delicassen")
```

## Generate a vector 
``` scala
// Generate an assembler vector of dataset features
val featureIndexer = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper",
"Delicassen")).setOutputCol("features")
```

## duplicate the data set
```scala
// aplicar el conjunto de datos del ensamblador de vectores
var  data = featureIndexer.transform (feature_data)
```

## Generate KMeans model
```scala
// Generar modelo KMeans para clustering indicando cuandos clusters se crearan y una semilla de aleatoriedad
// En este caso no se busca la prediccion de una variable dependiente por lo que no se asigna
val  kmeans  =  new  KMeans () .setK ( 3 ) .setSeed ( 1L )
```

## We train the model using our data
```scala
// Entrenamos el modelo utilizando nuestros datos
val  modelo  = kmeans.fit (datos)
```

## Within the set of the sum of squared errors
```scala
//   Dentro del conjunto de la suma de errores al cuadrado
val  WSSSE  = model.computeCost (datos)
println ( s " Dentro de la suma establecida de errores al cuadrado = $ WS SSE " )
```

## Show the result.
```scala
// Muestra el resultado.
println ( " Cluster Centers: " )
model.clusterCenters.foreach (println)
```
