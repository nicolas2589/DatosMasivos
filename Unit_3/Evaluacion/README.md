 # Practica 1

# Exercise 1
## Libraries
``` scala
//1.Se importan las librerías para poder trabajar con los archivos y herramientas
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}
```

# Exercise 2 
## Load Dataset
``` scala
//2.Load the data stored 
val dataset = spark.read.option("header", "true").option("inferSchema","true")csv("data.csv")
```

# Exercise 3 
## Get desired data 
``` scala
//3.Get desired data from the dataset 
val feature_data = dataset.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper","Delicassen")
```

# Exercise 4 
## Generate a vector 
``` scala
// Generate an assembler vector of dataset features
val featureIndexer = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper",
"Delicassen")).setOutputCol("features")

```
