# Practice 1

# Tome los datos

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

# Despligue los datos

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

## 
```scala

```
## 
```scala

```

```
