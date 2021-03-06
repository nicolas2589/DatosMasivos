# Practice 3

# Exercise 1
## libraries.
```scala
//The necessary libraries are imported
importar  org . apache . chispa . ml . Tubería
importar  org . apache . chispa . ml . clasificación . { RandomForestClassificationModel , RandomForestClassifier }
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase
importar  org . apache . chispa . ml . función . { IndexToString , StringIndexer , VectorIndexer }
```

# Exercise 2
## Load data.
```scala
// Load data
val  data  = spark.read.format ( " libsvm " ) .load ( " sample.txt " )
```

# Exercise 3
## Data Set to Clean.
```scala
// Data Set to Clean
val  labelIndexer  =  new  StringIndexer () .setInputCol ( " etiqueta " ) .setOutputCol ( " indexedLabel " ) .fit (datos)

val  featureIndexer  =  new  VectorIndexer () .setInputCol ( " características " ) .setOutputCol ( " indexedFeatures " ) .setMaxCategories ( 4 ) .fit (datos)
```

# Exercise 4
## Separate dataset.
```scala
// Separate dataset
val  Array (trainingData, testData) = data.randomSplit ( Array ( 0.7 , 0.3 ))
```

# Exercise 5
## characteristics of the model.
```scala
// Caracteristicas del modelo
val  rf  =  new  RandomForestClassifier () .setLabelCol ( " indexedLabel " ) .setFeaturesCol ( " indexedFeatures " ) .setNumTrees ( 10 )
```

# Exercise 6
## Translate dataset.
```scala
// Translate dataset
// val labelConverter = new IndexToString (). setInputCol ("predicción"). setOutputCol ("predictedLabel"). setLabels (labelIndexer.labelsArray (0))
```

# Exercise 7
## Generate a pipeline.
```scala 
// Generate a pipeline to clean the data and generate a model
val  pipeline  =  new  Pipeline () .setStages ( Array (labelIndexer, featureIndexer, rf))
```

# Exercise 8
## generated from the training data
```scala
// A model is generated from the training data
val  model  = pipeline.fit (trainingData)
```

# Exercise 9
## The model is taken and generated and evaluates data.
```scala
// The model is taken and generated and evaluates data
 predicciones de  val = model.transform (testData)
 ```
 
 # Exercise 10
## Show the result
```scala
// Show the result
predicciones mostrar ( 5 )
```

# Exercise 11
## Calculate the error
```scala
// Calculate the error
val  evaluator  =  new  MulticlassClassificationEvaluator () .setLabelCol ( " indexedLabel " ) .setPredictionCol ( " predicción " ) .setMetricName ( " precisión " )
val  precisión  = evaluador.evaluar (predicciones)
println ( s " Error de prueba = $ {( 1.0  - precisión)} " )
println ( s " Num de datos de prueba = $ {(predictions.count ())} " )
println ( s " Predicciones correctas = $ {(predictions.where ( " prediction == indexedLabel " ) .count ())} " )
println ( s " Predicciones fallidas = $ {(predictions.where ( " prediction! = indexedLabel " ) .count ())} " )
```

# Exercese 12
## Show the model.
```scala
// Show the model
val  rfModel  = model.stages ( 2 ). asInstanceOf [ RandomForestClassificationModel ]
// println (s "Modelo de bosque de clasificación aprendido: \ n $ {rfModel.toDebugString}")
```

#

# Test2RF

# Exercese 1
## libraries.
``` scala
//The necessary libraries are imported
importar  org . apache . chispa . ml . Tubería
importar  org . apache . chispa . ml . clasificación . { RandomForestClassificationModel , RandomForestClassifier }
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase
importar  org . apache . chispa . ml . función . { IndexToString , StringIndexer , VectorIndexer , VectorAssembler }
```

# Exercese 2
## Load data.
```scala
// Load data
val  dat  = spark.read.option ( " header " , " true " ) .option ( " inferSchema " ,
  " verdadero " ) csv ( " Titanic.csv " )
```

# Exercese 3
## Clear data set.
```scala
// Clear data set
val  sexIndexer  =  new  StringIndexer () .setInputCol ( " Sexo " ) .setOutputCol ( " iSex " ) .fit (dat)
val  nDf  = sexIndexer.transform (dat)
val  data = nDf.drop ( " PassengerId " , " Nombre " , " Sexo " , " Parch " , " Boleto " , " Cabina " , " Embarcado " )
val  featureIndexer  =  new  VectorAssembler () .setInputCols ( Array ( " iSex " , " Fare " , " Age " , " Pclass " , " SibSp " )). setOutputCol ( " features " )
val  dt = featureIndexer.transform (datos)
```

# Exercese 4
## Separate dataset
```scala
// Separate dataset
val  Array (trainingData, testData) = dt.randomSplit ( Array ( 0.7 , 0.3 ))
```

# Exercese 5
## Characteristics of the model.
```scala
// Characteristics of the model
// val rf = new DecisionTreeClassifier (). setLabelCol ("Sobrevivido"). setFeaturesCol ("características")
val  rf  =  new  RandomForestClassifier () .setLabelCol ( " Sobrevivido " ) .setFeaturesCol ( " características " ) .setNumTrees ( 10 )
// val pipeline = new Pipeline (). setStages (Array (rf))
val  modelo  = rf.fit (trainingData)
 predicciones de  val = model.transform (testData)
```

# Exercese 6
## Show the result.
```scala
// Show the result
predicciones mostrar ( 5 )
```

# Exercese 7
## Calculate the error.
```scala
// Calculate the error
val  evaluator  =  new  MulticlassClassificationEvaluator () .setLabelCol ( " Sobrevivido " ) .setPredictionCol ( " predicción " ) .setMetricName ( " precisión " )
val  precisión  = evaluador.evaluar (predicciones)
println ( s " Error de prueba = $ {( 1.0  - precisión)} " )
println ( s " Num de datos de prueba = $ {(predictions.count ())} " )
println ( s " Predicciones correctas = $ {(predictions.where ( " prediction == Sobrevivido " ) .count ())} " )
println ( s " Predicciones fallidas = $ {(predictions.where ( " prediction! = Survived " ) .count ())} " )
// Muestra el modelo
val  rfModel  = modelo. asInstanceOf [ RandomForestClassificationModel ]
// println (s "Modelo de bosque de clasificación aprendido: \ n $ {rfModel.toDebugString}")
```
