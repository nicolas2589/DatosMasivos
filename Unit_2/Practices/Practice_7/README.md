# Practice 7

# Exercise 1
## Import libraries
```sacla
import org.apache.spark.ml.classification.NaiveBayes
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
// Split the data into training and test sets (30% held out for testing)
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
```

# Exercise 4
## Train a NaiveBayes model.
```scala
// Train a NaiveBayes model.
val model = new NaiveBayes().fit(trainingData)
```

# Exercise 5
## Select example rows.
```scala
// Select example rows to display.
val predictions = model.transform(testData)
predictions.show()
```

# Exercise 6
## Select and compute test error.
```scala
// Select (prediction, true label) and compute test error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println("Test set accuracy = " + accuracy)
```
