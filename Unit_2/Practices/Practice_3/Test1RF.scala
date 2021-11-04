import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

//Cargar datos
val data = spark.read.format("libsvm").load("sample.txt")

//Limpiar dataset
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

//Separar dataset
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

//Caracteristicas del modelo
val rf = new RandomForestClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setNumTrees(10)

//Traducir Dataset
//val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))

//Genera un pipeline para limpiar los datos y generar modelo
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, rf))

//Se genera un modelo a partir de los datos de entrenamiento
val model = pipeline.fit(trainingData)

//Se toma el modelo y generado y evalua datos
val predictions = model.transform(testData)

//Muestra el resultado
predictions.show(5)

//Calcula el error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")
println(s"Num de datos de prueba = ${(predictions.count())}")
println(s"Predicciones correctas = ${(predictions.where("prediction == indexedLabel").count())}")
println(s"Predicciones fallidas = ${(predictions.where("prediction != indexedLabel").count())}")

//Muestra el modelo
val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
//println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
