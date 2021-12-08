# Practica 1

# Exercise 1
## Libraries
``` scala
//1.Se importan las librerías para poder trabajar con los archivos y herramientas
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
```

# Exercise 2 
## Load Dataset
``` scala
//2.Se procede a cargar los datos para su manipulación
// Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("sample.txt")
// Show data
//data.show()

```

# Exercise 3
## Index labels, adding metadata to the label column
``` scala
//3. Se acomodan los datos en el label para despues ser encapsulados. 
//Se identifican las características categóricas así como vector de características o vector features.

// Index labels, adding metadata to the label column.
// Fit on whole dataset to include all labels in index.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)
// Automatically identify categorical features, and index them.
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data) // features with > 4 distinct values are treated as continuous.
```
# Exercise 4
## Split the data into training and test sets
``` scala
//4. Se dividen los datos en 2 “Datos de entrenamiento” y “Datos de prueba”
// Split the data into training and test sets (30% held out for testing).
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```

# Exercise 5
## Train a DecisionTree model.
``` scala
//5. Generar un objeto DecisionTreeClassifier y especificar en el label y vector features
// Train a DecisionTree model.
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

// Convert indexed labels back to original labels.
//val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))
```
# Exercise 6
## Chain indexers and tree in a Pipeline.
``` scala
//6.Se genera un Pipeline que permite ejecutar el proceso de acomodar los datos y el crear el objeto de DecisionTree y se genera un modelo utilizando los datos de entrenamiento.
// Chain indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt))

// Train model. This also runs the indexers.
val model = pipeline.fit(trainingData)
```

# Exercise 7
## Make predictions.
``` scala
//7. Finalmente se prueba el modelo utilizando los datos de prueba y se obtienen todos los resultados como predicciones correctas, fallidas, y el error.
// Make predictions.
val predictions = model.transform(testData)

// Select example rows to display.
predictions.show(5)
```
# Exercise 8
## Select and compute test error.
``` scala
//7. Despues se elige y calcula el error de prueba 
// Select (prediction, true label) and compute test error.
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")
println(s"Num de datos de prueba = ${(predictions.count())}")
println(s"Predicciones correctas = ${(predictions.where("prediction == indexedLabel").count())}")
println(s"Predicciones fallidas = ${(predictions.where("prediction != indexedLabel").count())}")

val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
//println(s"Learned classification tree model:\n ${treeModel.toDebugString}")




