import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}

//1.- Carga el dataset
val dat = spark.read.option("header", "true").option("inferSchema","true")csv("iris.csv")
//2.- Nombre de las columnas
println(dat.columns.toSeq)
//3.- Esquema
println(dat.schema)
dat.printSchema()
//5.-Utilizar el metodo describe
dat.describe().show()

//4.- mostrar numero de columnas en este caso 5
dat.select(dat.columns.slice(0,5).map(m=>col(m)):_*).show()
// numero de renglones en este caso 5
dat.show(5)

//6.-Transformacion para datos categoricos y etiquetas a clasificar
//etiqueta
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(dat)
var dat1=labelIndexer.transform(dat)
//caracteristicas
val featureIndexer = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
var data=featureIndexer.transform(dat1)


// Separa el dataset en train y test
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)

dat.select("Species").groupBy("Species").count().show()

// Especifica las capas de la red neuronal
// input layer of size 4 (features), two intermediate of size 5 and 4
// and output of size 3 (classes)
val layers = Array[Int](4, 5, 4, 3)

//7-.Construya el modelo
// Crea el modelo ajustado a los parametros
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

// Entrenar el modelo
val model = trainer.fit(train)
//8.- Resultados del modelo
// Se prueba el modelo
//val result = model.transform(test)
val result = model.transform(test).select(col("*"),col("prediction").as("predictionIndex", data.schema("label").metadata))
val labelConverter = new IndexToString().setInputCol("predictionIndex").setOutputCol("predictedLabel")
val prediction = labelConverter.transform(result)
val labelsPrediction = prediction.select("species","predictedLabel")
labelsPrediction.show()
//Se obtiene la presicion
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
val accuracy = evaluator.evaluate(prediction)
println(s"Test Error = ${(1.0 - accuracy)}")
println(s"Test accuracy = ${accuracy}")
println(s"Num de datos de prueba = ${(prediction.count())}")
println(s"Predicciones correctas = ${(prediction.where("predictedLabel == species").count())}")
println(s"Predicciones fallidas = ${(prediction.where("predictedLabel != species").count())}")
