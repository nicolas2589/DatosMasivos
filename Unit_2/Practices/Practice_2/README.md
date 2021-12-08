# Practica 2

# Exercise 1
## Load training data
``` scala
//1.Se importan los datos para su manipulacion 
// Load training data
val training = spark.read.format("libsvm").load("sample.txt")
val lr = new LinearRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)

```

# Exercise 2 
## Fit the model.
``` scala
//2. Se carga el modelo
// Fit the model
val lrModel = lr.fit(training)
```

# Exercise 3
## Print the coefficients and intercept.
``` scala
//3. Imprimimos los coeficientes para poder analizarlos y darle una interpretacion.
// Print the coefficients and intercept for linear regression
println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

```
# Exercise 4
## Summarize the model over the training set and print out some metrics.
``` scala
//4. Al final se imprimen la metricas para el resultado final 
// Summarize the model over the training set and print out some metrics
val trainingSummary = lrModel.summary
println(s"numIterations: ${trainingSummary.totalIterations}")
println(s"objectiveHistory: [${trainingSummary.objectiveHistory.mkString(",")}]")
trainingSummary.residuals.show()
println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
println(s"r2: ${trainingSummary.r2}")
```

