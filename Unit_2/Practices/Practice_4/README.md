# Practice 4

# Exercise 1

```scala
//The data is loaded
val data = spark.read.format("libsvm").load("sample.txt")
```

# Exercise 2

```scala
//Clean data and assign label and features
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)
```

# Exercise 3

```scala
// Separate the array into training and test data
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```

# Exercise 4

```scala
// Define GBTClassifier model
val gbt = new GBTClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setMaxIter(10).setFeatureSubsetStrategy("auto")
```

# Note

```scala
// Traducir label indexados(No funciona en esta version?)
//val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))
```

# Exercise 5

```scala
//Define process order
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, gbt))
```

# Exercise 6

```scala
// The model is trained using the training data
val model = pipeline.fit(trainingData)
```

# Exercise 7

```scala
// A test is performed with the test data on the model
val predictions = model.transform(testData)
```

# Exercise 8

```sacla
// Show the results obtained from the prediction
predictions.show(5)
```

# Exercise 9

```scala
// Calculate the error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${1.0 - accuracy}")

val gbtModel = model.stages(2).asInstanceOf[GBTClassificationModel]
println(s"Learned classification GBT model:\n ${gbtModel.toDebugString}")
```
