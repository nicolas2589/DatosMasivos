 # Evaluation 3

# Libraries
``` scala
//1.Se importan las librerías para poder trabajar con los archivos y herramientas
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}
```

# Load Dataset
``` scala
//2.Load the data stored
val dataset = spark.read.option("header", "true").option("inferSchema","true")csv("data.csv")
```

# Get desired data
``` scala
//3.Get desired data from the dataset
val feature_data = dataset.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper","Delicassen")
```

# Generate a vector
``` scala
// 4.Generate an assembler vector of dataset features
val featureIndexer = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper",
"Delicassen")).setOutputCol("features")
```

# Duplicate the data set
```scala
// 5.aplicar el conjunto de datos del ensamblador de vectores
var  data = featureIndexer.transform (feature_data)
```

# Generate KMeans model
This is responsible for grouping objects in k (assigned variable) groups based on their characteristics.
```scala
// Generar modelo KMeans para clustering indicando cuandos clusters se crearan y una semilla de aleatoriedad
// En este caso no se busca la prediccion de una variable dependiente por lo que no se asigna
val  kmeans  =  new  KMeans () .setK ( 3 ) .setSeed ( 1L )
```

# We train the model using our data
```scala
// Entrenamos el modelo utilizando nuestros datos
val  modelo  = kmeans.fit (datos)
```

# Within the set of the sum of squared errors
SSE refers to the sum of the squared differences between each observation and the group average, it is used to measure the variation of the cluster.
and finally the centroids are printed.
```scala
//   Dentro del conjunto de la suma de errores al cuadrado
val  WSSSE  = model.computeCost (datos)
println ( s " Dentro de la suma establecida de errores al cuadrado = $ WS SSE " )
```

# Show the result.
```scala
// Muestra el resultado.
println ( " Cluster Centers: " )
model.clusterCenters.foreach (println)
```
# Results
We can finally display the results showing us the cluster centers and the wssse or variance of the cluster.
We find 3 centroids with 6 numbers, this means 6 differents features are evaluated in the process.
```console
Within Set Sum of Squared Errors = 8.095172370767671E10
Cluster Centers:
[7993.574780058651,4196.803519061584,5837.4926686217,2546.624633431085,2016.2873900293255,1151.4193548387098]
[9928.18918918919,21513.081081081084,30993.486486486487,2960.4324324324325,13996.594594594595,3772.3243243243246]
[35273.854838709674,5213.919354838709,5826.096774193548,6027.6612903225805,1006.9193548387096,2237.6290322580644]
```
