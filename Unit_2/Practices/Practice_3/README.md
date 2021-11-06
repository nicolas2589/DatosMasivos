# Test1RF

# Practice 3

# Exercise 1

```scala
//The necessary libraries are imported
importar  org . apache . chispa . ml . Tubería
importar  org . apache . chispa . ml . clasificación . { RandomForestClassificationModel , RandomForestClassifier }
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase
importar  org . apache . chispa . ml . función . { IndexToString , StringIndexer , VectorIndexer }
```

# Exercise 2

```scala
// Load data
val  data  = spark.read.format ( " libsvm " ) .load ( " sample.txt " )
```

# Exercise 3

```scala
// Data Set to Clean
val  labelIndexer  =  new  StringIndexer () .setInputCol ( " etiqueta " ) .setOutputCol ( " indexedLabel " ) .fit (datos)

val  featureIndexer  =  new  VectorIndexer () .setInputCol ( " características " ) .setOutputCol ( " indexedFeatures " ) .setMaxCategories ( 4 ) .fit (datos)
```

# Exercise 4

```scala
// Separate dataset
val  Array (trainingData, testData) = data.randomSplit ( Array ( 0.7 , 0.3 ))
```

# Exercise 5

```scala
// Caracteristicas del modelo
val  rf  =  new  RandomForestClassifier () .setLabelCol ( " indexedLabel " ) .setFeaturesCol ( " indexedFeatures " ) .setNumTrees ( 10 )
```

# Exercise 6

```scala
// Translate dataset
// val labelConverter = new IndexToString (). setInputCol ("predicción"). setOutputCol ("predictedLabel"). setLabels (labelIndexer.labelsArray (0))
```

# Exercise 7

```scala 
// Generate a pipeline to clean the data and generate a model
val  pipeline  =  new  Pipeline () .setStages ( Array (labelIndexer, featureIndexer, rf))
```

# Exercise 8

```scala
// A model is generated from the training data
val  model  = pipeline.fit (trainingData)
```

# Exercise 9
```scala
// The model is taken and generated and evaluates data
 predicciones de  val = model.transform (testData)
 ```
 
 # Exercise 10

```scala
// Show the result
predicciones mostrar ( 5 )
```

# Exercise 11

```scala
// Calculate the error
val  evaluator  =  new  MulticlassClassificationEvaluator () .setLabelCol ( " indexedLabel " ) .setPredictionCol ( " predicción " ) .setMetricName ( " precisión " )
val  precisión  = evaluador.evaluar (predicciones)
println ( s " Error de prueba = $ {( 1.0  - precisión)} " )
println ( s " Num de datos de prueba = $ {(predictions.count ())} " )
println ( s " Predicciones correctas = $ {(predictions.where ( " prediction == indexedLabel " ) .count ())} " )
println ( s " Predicciones fallidas = $ {(predictions.where ( " prediction! = indexedLabel " ) .count ())} " )
```

# Exircese 12

```scala
// Show the model
val  rfModel  = model.stages ( 2 ). asInstanceOf [ RandomForestClassificationModel ]
// println (s "Modelo de bosque de clasificación aprendido: \ n $ {rfModel.toDebugString}")
```


# Test2RF

# Exircese 1

``` scala
//The necessary libraries are imported
importar  org . apache . chispa . ml . Tubería
importar  org . apache . chispa . ml . clasificación . { RandomForestClassificationModel , RandomForestClassifier }
importar  org . apache . chispa . ml . evaluación . Evaluador de clasificación multiclase
importar  org . apache . chispa . ml . función . { IndexToString , StringIndexer , VectorIndexer , VectorAssembler }
```

# Exircese 2

```scala
// Load data
val  dat  = spark.read.option ( " header " , " true " ) .option ( " inferSchema " ,
  " verdadero " ) csv ( " Titanic.csv " )
```

# Exircese 3

```scala
// Clear data set
val  sexIndexer  =  new  StringIndexer () .setInputCol ( " Sexo " ) .setOutputCol ( " iSex " ) .fit (dat)
val  nDf  = sexIndexer.transform (dat)
val  data = nDf.drop ( " PassengerId " , " Nombre " , " Sexo " , " Parch " , " Boleto " , " Cabina " , " Embarcado " )
val  featureIndexer  =  new  VectorAssembler () .setInputCols ( Array ( " iSex " , " Fare " , " Age " , " Pclass " , " SibSp " )). setOutputCol ( " features " )
val  dt = featureIndexer.transform (datos)
```

# Exircese 4

```scala
// Separate dataset
val  Array (trainingData, testData) = dt.randomSplit ( Array ( 0.7 , 0.3 ))
```

# Exircese 5

```scala
// Characteristics of the model
// val rf = new DecisionTreeClassifier (). setLabelCol ("Sobrevivido"). setFeaturesCol ("características")
val  rf  =  new  RandomForestClassifier () .setLabelCol ( " Sobrevivido " ) .setFeaturesCol ( " características " ) .setNumTrees ( 10 )
// val pipeline = new Pipeline (). setStages (Array (rf))
val  modelo  = rf.fit (trainingData)
 predicciones de  val = model.transform (testData)
```

# Exircese 6

```scala
// Show the result
predicciones mostrar ( 5 )
```

# Exircese 7

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
