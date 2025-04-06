package data.pipeline.components

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Created by Ravi Chiripurapu on 3/26/25.
 */
object Example11 {

  case class Customer(id: String, customer_name: String,
                      street_number: String, street_name: String,
                      city: String, state: String, zip: String)

  def main(args:Array[String]) =
  {

    System.setProperty("hadoop.home.dir", "/Users/ravi/ScalaProjects/enrich/spark-warehouse")

    val spark = SparkSession.builder()
      .appName("Spark SQL Example")
      .config("spark.master", "local[*]")
      .getOrCreate()

    // Read the input CSV file into a DataFrame
    val inputFile = "/Users/ravi/ScalaProjects/enrich/data/customer/customers.csv"

    val df: DataFrame = spark.read.option("header", "true").csv(inputFile)

    df.show()

    import spark.implicits._
    val customerDF = df.as[Customer]

    customerDF.createOrReplaceTempView("customers")

    var output = spark.sql("select * from customers where city = 'Sacramento'")

    output.show()

    output.
      write
      .format("com.databricks.spark.csv")
      .mode("overwrite").csv("/Users/ravi/ScalaProjects/enrich/data/results/customers.csv")

    }
}
