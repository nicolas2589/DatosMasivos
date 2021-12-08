import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

//Cargar datos
val data = spark.read.format("libsvm").load("sample.txt")

//Limpiar datos y asignar label y features
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

// Separar el arreglo en datos de entrenamiento y prueba
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

// Definir modelo GBTClassifier
val gbt = new GBTClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setMaxIter(10).setFeatureSubsetStrategy("auto")

// Traducir label indexados(No funciona en esta version?)
//val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))

//Definir orden de proceso
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, gbt))

// Se entrena el modelo utilizando los datos de entrenamiento
val model = pipeline.fit(trainingData)

// Se realiza un test con los datos de prueba en el modelo
val predictions = model.transform(testData)

// Mostrar los resultados obtenidos de la prediccion
predictions.show(5)

// Calcular el error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${1.0 - accuracy}")

val gbtModel = model.stages(2).asInstanceOf[GBTClassificationModel]
println(s"Learned classification GBT model:\n ${gbtModel.toDebugString}")
