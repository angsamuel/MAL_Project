import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._



object MAL_Main extends App {
   
 val spark = SparkSession.builder().master("local[*]").getOrCreate()
 import spark.implicits._
 spark.sparkContext.setLogLevel("WARN")

  val anime_schema = StructType(Array(
    StructField("anime_id", IntegerType),
    StructField("name", StringType),
    StructField("genre", StringType),
    StructField("type", StringType),
    StructField("episodes", DoubleType),
    StructField("rating", DoubleType),
    StructField("members", DoubleType)
  ))
  
  val review_schema = StructType(Array(
    StructField("user_id", IntegerType),
    StructField("anime_id", IntegerType),
    StructField("rating", DoubleType)
  ))
 
  //read titles and reviews into dataframe
  val anime_dataframe = spark.read.schema(anime_schema).option("header", true).csv("../data/anime.csv")
  
  //read reviews into dataframe
  val review_dataframe = spark.read.schema(review_schema).option("header", true).csv("../data/rating.csv")
  
  //unique_user_ids
  val unique_user_ids = review_dataframe.select('user_id).distinct().distinct().count()
  
  //average review score
  val average_user_rating = review_dataframe.filter('rating > -1).select(avg('rating)).collect()(0)
  
  //
  val recommender = new Recommendation(anime_dataframe, review_dataframe.filter('user_id < 300).filter('rating > -1))
  recommender.GiveRecs(5)
  
  
  
  
  //General Info-----------------------------------
  println("\n\n\nWELCOME TO MAL_PROJECT -- GENERAL")
  println("Total Number of Titles: " + anime_dataframe.count())
  println("Total Number of Reviews: " + review_dataframe.filter('rating > -1).count())
  println("Total Number of Users: " + unique_user_ids)
  println("Average User Rating: " + average_user_rating)
  
  
  
    
 spark.close()
}