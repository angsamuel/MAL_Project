
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame

import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.functions._
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import scala.io.Source
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Encoders
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.functions.countDistinct
import scala.collection.mutable.ListBuffer
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS

class Recommendation (val anime_dataframe: DataFrame, val review_dataframe: DataFrame) {
  
  
  
  
  def GiveRecs(num_recs: Int){
    
    val Array(training, test) = review_dataframe.randomSplit(Array(0.8, 0.2))

    val als = new ALS()
      .setMaxIter(5)
      .setRegParam(0.01)
      .setUserCol("user_id")
      .setItemCol("anime_id")
      .setRatingCol("rating")
    
    val model = als.fit(training)
    model.setColdStartStrategy("drop")
    val predictions = model.transform(test)

    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    
      val rmse = evaluator.evaluate(predictions)
      println(s"Root-mean-square error = $rmse")
      
      val userRecs = model.recommendForAllUsers(num_recs)
      userRecs.show()
      
      var pretty_recs = userRecs.select(explode(col("recommendations"))).collect().map(x => x.toString().split(",")(0).replace("[","").toInt)
      pretty_recs.take(5) foreach println
      var pg = pretty_recs.groupBy(x => x).toArray.map(x=>(x._1, x._2.size)).sortWith(_._2 > _._2)
      pg.take(num_recs) foreach println
      
      //userRecs.collect().toArray.map(x => (x.getInt(0), x.getDouble(1))).toArray.groupBy(_._1).mapValues(_.map(_._2).sum).toSeq.sortBy(_._2).take(5) foreach println
      
    println("ALL DONE!!!!!!!!")
    
  }
  
  
  
  
  
  
  
}