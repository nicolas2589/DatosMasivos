import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}

// Cargar datos
val dataset = spark.read.option("header", "true").option("inferSchema","true")csv("data.csv")

// Obtener datos deseados del dataset
val feature_data = dataset.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper","Delicassen")

// Generar un vector assembler de caracteristicas del dataset
val featureIndexer = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper",
"Delicassen")).setOutputCol("features")

// aplicar el vector assembler al dataset
var data=featureIndexer.transform(feature_data)

// Generar modelo KMeans para clustering indicando cuandos clusters se crearan y una semilla de aleatoriedad
// En este caso no se busca la prediccion de una variable dependiente por lo que no se asigna
val kmeans = new KMeans().setK(3).setSeed(1L)

// Entrenamos el modelo utilizando nuestro data
val model = kmeans.fit(data)

//  Within Set Sum of Squared Errors
val WSSSE = model.computeCost(data)
println(s"Within Set Sum of Squared Errors = $WSSSE")

// Shows the result.
println("Cluster Centers: ")
model.clusterCenters.foreach(println)
