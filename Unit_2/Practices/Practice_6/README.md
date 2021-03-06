# Practice 6

# Exercise 1
## Import libraries.
```scala
import org.apache.spark.ml.classification.LinearSVC
```

# Exercise 2
## Load training data.
```scala
// Load training data
val training = spark.read.format("libsvm").load("sample.txt")

val lsvc = new LinearSVC().setMaxIter(10).setRegParam(0.1)
```
# Exercise 3
## Fit the model.
```scala
// Fit the model
val lsvcModel = lsvc.fit(training)
```

# Exercise 4
## Print the coefficients.
```scala
// Print the coefficients and intercept for linear svc
println(s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")
```
