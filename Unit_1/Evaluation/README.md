# Evaluation 1


# Exercise 1
Start a new spark session 
``` scala
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

```

# Exercise 2
Load the Netflix Csv file, and make spark infer the data
``` scala
val NetflixDf = spark.read.option("header", "true").option("inferSchema",
  "true")csv("Netflix_2011_2016.csv")

```

# Exercise 3
What are the name of the columns
```scala
NetflixDf.columns

```

# Exercise 4
how is the schema? 

``` scala
NetflixDf.printSchema()

```

# Exercise 5
Print the first 5 columns by name
``` scala
//imprimir las 5 columnas por nombre
NetflixDf.select($"Date",$"Open",$"High",$"Low", $"Close").show()
//Restar 2 columnas por nombre
NetflixDf.drop("Volume", "Adj Close").show()
```

# Exercise 6
use describe() function to learn about the dataframe

``` scala
NetflixDf.describe()

```

# Exercise 7
Create a new data frame with a new column "HV Ratio", this is the relation betwen column "High" and "Volume"
``` scala
var NewDf = NetflixDf.withColumn("HV Ratio", NetflixDf("High")/NetflixDf("Volume"))

```

# Exercise 8
What day have the maximum "Close" value

``` scala
val DiaDf = NetflixDf.withColumn("Day", dayofmonth(NetflixDf("Date")))

val MaxDf = DiaDf.groupBy("Day").max()
MaxDf.printSchema()

MaxDf.select($"Day", $"max(Close)").show()
MaxDf.select($"Day", $"max(Close)").sort(desc("max(Close)")).show()
MaxDf.select($"Day", $"max(Close)").sort(desc("max(Close)")).show(1)
```

# Exercise 9
With your own words describe, what "Close" column means
``` scala
//La columna “Close” representa el valor de mercado con el que la plataforma Netflix cerró el día.
```

# Exercise 10
Whats the maximum and minimum value on "Volume" column?

``` scala
NetflixDf.select(max("Volume"), min("Volume")).show()

```


# Exercise 11
Using scala/spark $ sintaxis answer:
``` scala
//a. ¿Cuántos días fue la columna “Close” inferior a $ 600?
NetflixDf.filter($"Close" < 600).count()

//b. ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500?
val dias = NetflixDf.filter($"High" > 500).count().toDouble
val porcentaje = ((dias / NetflixDf.count())*100) 

//c. ¿Cuál es la correlación de Pearson entre columna “High” y la columna “Volumen”?
NetflixDf.select(corr($"High", $"Volume")).show()

//d. ¿Cuál es el máximo de la columna “High” por año?
val MaxHDf = NetflixDf.withColumn("year", year(NetflixDf("Date")))

val MaxHPDf= MaxHDf.groupBy("year").max()
MaxHPDf.select($"year", $"max(High)").show()
MaxHPDf.select($"year", $"max(High)").show(1)

//e. ¿Cuál es el promedio de columna “Close” para cada mes del calendario?
val MesDf = NetflixDf.withColumn("Month", month(NetflixDf("Date")))
val MeanDf = MesDf.groupBy("Month").mean()
MeanDf.select($"Month", $"avg(Close)").sort(asc("Month")).show()

```
