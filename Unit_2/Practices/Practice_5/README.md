# Practice 5

# Exercise 1
## Immport libraries.
```scala
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
```

# Exercise 2
## Load the data.
```scala
// Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("sample.txt")
```

# Exercise 3
## Split the data.
```scala
// Split the data into train and test
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)
```

# Exercise 4
## Specify the layers of the neural network.
```scala
// Especifica las capas de la red neuronal
// input layer of size 4 (features), two intermediate of size 5 and 4
// and output of size 3 (classes)
val layers = Array[Int](4, 5, 4, 3)
```

# Exercise 5
## Create the model fitted to the parameters.
```scala
// Crea el modelo ajustado a los parametros
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
```

# Exercise 6
## Train the model.
```scala
// Entrenar el modelo
val model = trainer.fit(train)
```

# Exercise 7
## compute accuracy on the test set and print
```scala
// compute accuracy on the test set
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
```
