import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
//Librerias de modelos de classificacion
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier

// Carga el dataset
var dat = spark.read.option("header", "true").option("inferSchema","true").option("delimiter", ";")csv("bank-full.csv")

// Esquema
dat.printSchema()

// mostrar el dataset
dat.show()

// se genera variables que ayudara a realizar la categorizacion de datos
val y = "y"
val columns = dat.columns
val numCols = dat.columns.size
var indexedcolumns = new Array[String](numCols)
var i = 0
// Genera la categorizacion de datos a cada columna y almacena el nombre de las nuevas columnas para uso posterior
for( x <- columns ){
   var labelIndexer = new StringIndexer().setInputCol(x).setOutputCol(x + "_index").fit(dat)
   dat = labelIndexer.transform(dat)
   indexedcolumns(i)=x + "_index"
   i+=1
}
//Utilizando expresiones regulares, obtener un dataset con todos los datos indexados
var idat=dat.select(dat.colRegex("`^.*_index*`"))

// Se cambia el nombre de la columna de la variable independiente y se elimina su nombre de la lista de columnas
idat = idat.withColumnRenamed(y + "_index","indexedLabel")
val indexedcolumnsfeatures = indexedcolumns.filter(! _.contains(y + "_index"))

// Generar vector assembler de los datos previamente indexados
val featureIndexer = new VectorAssembler().setInputCols(indexedcolumnsfeatures).setOutputCol("indexedFeatures")

// Aplica el vector assembler utilizando el dataset
var data=featureIndexer.transform(idat)

// Se limpia el dataset para tener solo el label y features
data = data.select("indexedLabel","indexedFeatures")

///////////////////////DecisionTreeClassifier/////////////////////////////////
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

//////////////////////////Multilayer Perceptron///////////////////////////////
for( x <- 0 to 29 ){
  // separar los datos en entrenamiento y prueba
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
  // Genera las capas del modelo
  // input layer of size 16 (features), two intermediate of size 10 and 5
  // and output of size 2 (classes)
  val layers = Array[Int](17, 10, 10, 2)
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
