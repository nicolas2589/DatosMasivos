//Responder las siguientes preguntas con Spark DataFrames y Scala utilizando el “CSV”
//Netflix_2011_2016.csv que se encuentra el la carpeta de spark-dataframes.

//1. Comienza una simple sesión Spark.
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

//2. Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos.
val NetflixDf = spark.read.option("header", "true").option("inferSchema",
  "true")csv("Netflix_2011_2016.csv")

//3. ¿Cuáles son los nombres de las columnas?
NetflixDf.columns

//4. ¿Cómo es el esquema?
NetflixDf.printSchema()

//5. Imprime las primeras 5 columnas.
//imprimir las 5 columnas por nombre
NetflixDf.select($"Date",$"Open",$"High",$"Low", $"Close").show()
//Restar 2 columnas por nombre
NetflixDf.drop("Volume", "Adj Close").show()

//6. Usa describe () para aprender sobre el DataFrame.
NetflixDf.describe()

//7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la
//relación entre el precio de la columna “High” frente a la columna “Volume” de
//acciones negociadas por un día. (Hint: Es una operación de columnas).
var NewDf = NetflixDf.withColumn("HV Ratio", NetflixDf("High")/NetflixDf("Volume"))

//8. ¿Qué día tuvo el pico mas alto en la columna “Close”?
val DiaDf = NetflixDf.withColumn("Day", dayofmonth(NetflixDf("Date")))

val MaxDf = DiaDf.groupBy("Day").max()
MaxDf.printSchema()

MaxDf.select($"Day", $"max(Close)").show()
MaxDf.select($"Day", $"max(Close)").sort(desc("max(Close)")).show()
MaxDf.select($"Day", $"max(Close)").sort(desc("max(Close)")).show(1)


//9. Escribe con tus propias palabras en un comentario de tu codigo. ¿Cuál es el
//significado de la columna Cerrar “Close”?

///La columna “Close” representa el valor de mercado con el que la plataforma Netflix cerró el día.

//10. ¿Cuál es el máximo y mínimo de la columna “Volume”?
NetflixDf.select(max("Volume"), min("Volume")).show()

//11.Con Sintaxis Scala/Spark $ conteste los siguiente:
//◦ Hint: Basicamente muy parecido a la session de dates, tendran que crear otro
//dataframe para contestar algunos de los incisos.

//a. ¿Cuántos días fue la columna “Close” inferior a $ 600?
NetflixDf.filter($"Close" < 600).count()

//b. ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500?
val dias = NetflixDf.filter($"High" > 500).count().toDouble
val porcentaje = ((dias / NetflixDf.count())*100) // 4.92% aprox

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
