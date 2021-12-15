 # Evaluation 4

# Libraries
``` scala
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
//Librerias de modelos de classificacion
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
```

# Load Dataset
Dataset description
external link https://archive.ics.uci.edu/ml/datasets/Bank+Marketing


``` scala
// Carga el dataset
var dat = spark.read.option("header", "true").option("inferSchema","true").option("delimiter", ";")csv("bank-full.csv")
dat = dat.withColumnRenamed("y","label")
// Esquema
dat.printSchema()
```

# Show dataset
```scala
// mostrar el dataset
dat.show()
```

# Support variables for data cleansing
```scala
// se genera variables que ayudara a realizar la categorizacion de datos
val y = "label"
val columns = dat.columns
val numCols = dat.columns.size
var indexedcolumns = new Array[String](numCols)
var i = 0
```

# Cycle that iterates through each column of the dataset and indexes each column
```scala
// Genera la categorizacion de datos a cada columna y almacena el nombre de las nuevas columnas para uso posterior
for( x <- columns ){
   var labelIndexer = new StringIndexer().setInputCol(x).setOutputCol(x + "_index").fit(dat)
   dat = labelIndexer.transform(dat)
   indexedcolumns(i)=x + "_index"
   i+=1
}
```

# Select only indexed columns for further use
```scala
//Utilizando expresiones regulares, obtener un dataset con todos los datos indexados
var idat=dat.select(dat.colRegex("`^.*_index*`"))
```

# It takes the previously specified independent variable and changes its name for later use, its name is also removed from the list of indexed columns
```scala
// Se cambia el nombre de la columna de la variable independiente y se elimina su nombre de la lista de columnas
idat = idat.withColumnRenamed(y + "_index","indexedLabel")
val indexedcolumnsfeatures = indexedcolumns.filter(! _.contains(y + "_index"))
```

# An assembler vector is generated that joins each of the indexed columns to later be treated as characteristics.
```scala
// Generar vector assembler de los datos previamente indexados
val featureIndexer = new VectorAssembler().setInputCols(indexedcolumnsfeatures).setOutputCol("indexedFeatures")

// Aplica el vector assembler utilizando el dataset
var data=featureIndexer.transform(idat)
```

# The dataset is cleaned to have only the label and features
```scala
// Se limpia el dataset para tener solo el label y features
data = data.select("indexedLabel","indexedFeatures")
```

# DecisionTreeClassifier algorithm application
```scala
/////////////////////DecisionTreeClassifier/////////////////////////////////
for( x <- 0 to 29 ){
  // separar los datos en entrenamiento y prueba
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
  // Genera modelo de arbol de desicion
  var dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setMaxBins(7168)
  // Entrena modelo
  var model = dt.fit(trainingData)
  // Pone a prueba el modelo
  var predictions = model.transform(testData)
  var evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
  var accuracy = evaluator.evaluate(predictions)
  println("Iteracion " + (x+1) + "," + (1.0 - accuracy) + "," + accuracy + "," + (predictions.where("prediction == indexedLabel").count()) + "," + (predictions.where("prediction != indexedLabel").count()))
}
```

# Application of the SupportVectorMachine algorithm
```scala
/////////////////////////SupportVectorMachine/////////////////////////////////
for( x <- 0 to 29 ){
  // separar los datos en entrenamiento y prueba
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
  // Genera modelo Suppor Vector
  val lsvc = new LinearSVC().setMaxIter(10).setRegParam(0.1).setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
  // Entrenar el modelo
  val model = lsvc.fit(trainingData)
  // Pone a prueba el modelo
  var predictions = model.transform(testData)
  var evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
  var accuracy = evaluator.evaluate(predictions)
  println("Iteracion " + (x+1) + "," + (1.0 - accuracy) + "," + accuracy + "," + (predictions.where("prediction == indexedLabel").count()) + "," + (predictions.where("prediction != indexedLabel").count()))
}
```

# Application of the Multinomial Logistic Regression algorithm
```scala
////////////////////Multinomial Logistic Regression///////////////////////////
for( x <- 0 to 29 ){
  // separar los datos en entrenamiento y prueba
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
  // Genera modelo de regresion logistica
  val lr = new LogisticRegression().setMaxIter(15).setRegParam(0.2).setElasticNetParam(0.7).setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
  // Entrenar el modelo
  val model = lr.fit(trainingData)
  // Pone a prueba el modelo
  var predictions = model.transform(testData)
  var evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
  var accuracy = evaluator.evaluate(predictions)
  println("Iteracion " + (x+1) + "," + (1.0 - accuracy) + "," + accuracy + "," + (predictions.where("prediction == indexedLabel").count()) + "," + (predictions.where("prediction != indexedLabel").count()))
}
```

# Application of the Multilayer Perceptron algorithm
```scala
//////////////////////////Multilayer Perceptron///////////////////////////////
for( x <- 0 to 29 ){
  // separar los datos en entrenamiento y prueba
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
  // Genera las capas del modelo
  // input layer of size 16 (features), two intermediate of size 10 and 5
  // and output of size 2 (classes)
  val layers = Array[Int](16, 10, 10, 2)
  // Generar modelo de MultilayerPerceptronClassifier
  val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(2589L).setMaxIter(100).setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
  // Entrenar
  val model = trainer.fit(trainingData)
  // Pone a prueba el modelo
  var predictions = model.transform(testData)
  var evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
  var accuracy = evaluator.evaluate(predictions)
  println("Iteracion " + (x+1) + "," + (1.0 - accuracy) + "," + accuracy + "," + (predictions.where("prediction == indexedLabel").count()) + "," + (predictions.where("prediction != indexedLabel").count()))
}
```

# Analysis
In the following table you can see the set of results where the average is observed after 30 iterations, the total data and total correct data to obtain a manual average of the precision, which compared to the average generated by the model, is the same, this just to corroborate.
As can be seen in the table, the results generated were similar among the 4 models, hovering between 88% precision, with the highest precision Support vector machine by 1 tenth, and the one with the highest Decision tree classifier error.
So we can say that the complexity of the model does affect its results.

![](https://github.com/nicolas2589/DatosMasivos/blob/Unidad_4/Unit_4/Project/Capture7.PNG)
