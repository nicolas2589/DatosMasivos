import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}

//Cargar datos
val dat = spark.read.option("header", "true").option("inferSchema",
  "true")csv("Titanic.csv")

//Limpiar dataset
val sexIndexer = new StringIndexer().setInputCol("Sex").setOutputCol("iSex").fit(dat)
val nDf = sexIndexer.transform(dat)
val data=nDf.drop("PassengerId","Name","Sex","Parch","Ticket","Cabin","Embarked")
val featureIndexer = new VectorAssembler().setInputCols(Array("iSex","Fare","Age","Pclass","SibSp")).setOutputCol("features")
val dt=featureIndexer.transform(data)

//Separar dataset
val Array(trainingData, testData) = dt.randomSplit(Array(0.7, 0.3))

//Caracteristicas del modelo
//val rf = new DecisionTreeClassifier().setLabelCol("Survived").setFeaturesCol("features")
val rf = new RandomForestClassifier().setLabelCol("Survived").setFeaturesCol("features").setNumTrees(10)
//val pipeline = new Pipeline().setStages(Array(rf))
val model = rf.fit(trainingData)
val predictions = model.transform(testData)

//Muestra el resultado
predictions.show(5)

//Calcula el error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("Survived").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")
println(s"Num de datos de prueba = ${(predictions.count())}")
println(s"Predicciones correctas = ${(predictions.where("prediction == Survived").count())}")
println(s"Predicciones fallidas = ${(predictions.where("prediction != Survived").count())}")
//Muestra el modelo
val rfModel = model.asInstanceOf[RandomForestClassificationModel]
//println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
