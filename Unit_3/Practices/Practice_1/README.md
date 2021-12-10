# Practice 1

# Take the data

## Import a SparkSession with the Logistic Regression library
```scala
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.log4j._
```

## Use the Error reporting code.
```scala
Logger.getLogger("org").setLevel(Level.ERROR)ew LinearSVC().setMaxIter(10).setRegParam(0.1)
```

## Create a Spark session
```scala
val spark = SparkSession.builder().getOrCreate()
```

## Use Spark to read the Advertising csv file.
```scala
val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("advertising.csv")
```

## Print the Schema of the DataFrame
```scala
data.printSchema()
```

# Deploy the data

## Print an example line
```scala
data.head(1)

val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example data row")
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}
```
# Prepare the DataFrame for Machine Learning

## Rename the column "Clicked on Ad" to "label"
```scala
// Take the following columns as features "Daily Time Spent on Site", "Age", "Area Income", "Daily Internet Usage", "Timestamp", "Male"
val logregdata = timedata.select(data("Clicked on Ad").as("label"), $"Daily Time Spent on Site", $"Age", $"Area Income", $"Daily Internet Usage", $"Hour", $"Male")
```

## Create a new column called "Hour" from the Timestamp containing the "Hour of the click"
```scala
val timedata = data.withColumn("Hour",hour(data("Timestamp")))
```

```scala
val  logregdata  = timedata.select (data ( "Se hizo clic en el anuncio " ) .as ( " etiqueta " ), $ " Tiempo diario invertido en el sitio " , $ " Edad " , $ " Ingresos del área " , $ " Uso diario de Internet " , $ " Hora " , $ " Hombre " )
```
## We import VectorAssembler and Vectors
```scala
importar  org . apache . chispa . ml . característica . VectorAssembler
importar  org . apache . chispa . ml . linalg . Vectores
```

## We create a new VectorAssembler object called assembler for the features
```scala
val  ensamblador  = ( nuevo  VectorAssembler ()
                  .setInputCols ( Array ( " Tiempo diario de permanencia en el sitio " , " Edad " , " Ingresos del área " , " Uso diario de Internet " , " Hora " , " Hombre " ))
                  .setOutputCol ( " características " ))
```

## We use randomSplit to create train and test data divided into 70/30
```scala
val  Array (entrenamiento, prueba) = logregdata.randomSplit ( Array ( 0.7 , 0.3 ), semilla =  12345 )
```

## Configure a Pipeline


## We import Pipeline
```scala
importar  org . apache . chispa . ml . Tubería
```

## We create a new LogisticRegression object called "lr"
```scala
val  lr  =  nueva  regresión logística ()
```

## We create a new pipeline with the elements: assembler, "lr"
```scala
val  pipeline  =  new  Pipeline () .setStages ( Array (ensamblador, lr))
```

## We adjust the pipeline for the training set.
```scala
val  model  = pipeline.fit (entrenamiento)
```

## We take the results in the test set with "transform"
```scala
val  resultados  = model.transform (prueba)
```

## Model evaluation

## We import MulticlassMetrics
```scala
importar  org . apache . chispa . mllib . evaluación . MulticlassMetrics
```

## We convert the test results into RDD using .as and .rdd
```scala
val  predictionAndLabels  = results.select ($ " prediction " , $ " label " ) .as [( Double , Double )]. rdd
```

## We initialize a MulticlassMetrics object
```scala
val  metrics  =  new  MulticlassMetrics (predictionAndLabels)
```

## We print Confusion matrix
```scala
println ( " Matriz de confusión: " )
println (metrics.confusionMatrix)

metrics.accuracy
```









