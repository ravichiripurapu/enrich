package data.pipeline.components

import org.apache.spark.sql.types.DecimalType
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession, functions => F}

/**
 * Created by Ravi Chiripurapu on 3/26/25.
 */
object Example12 {

  case class Sales(sales_ts: String, customer_id: String,
                   sales_amount: BigDecimal)

  def main(args:Array[String]) =
  {

    System.setProperty("hadoop.home.dir", "/Users/ravi/ScalaProjects/enrich/spark-warehouse")

    val spark = SparkSession.builder()
      .appName("Spark SQL Example")
      .config("spark.master", "local[*]")
      .getOrCreate()

    // Read the input CSV file into a DataFrame
    val inputFile = "/Users/ravi/ScalaProjects/enrich/data/sales"

    val df: DataFrame = spark.read.option("header", "true").csv(inputFile)

    import spark.implicits._

    // Cast 'amount' column to DecimalType(15, 4) with higher precision
    val dfWithDecimal = df.withColumn("sales_amount", F.col("sales_amount").cast(DecimalType(15, 4)))

    // Convert DataFrame to Dataset of Sales objects (strongly typed)
    val customerDF: Dataset[Sales] = dfWithDecimal.as[Sales]

    customerDF.createOrReplaceTempView("sales")

    var output = spark.sql("select sum(sales_amount) from sales where customer_id = '1'")

    output.show()

    output.
      write
      .format("com.databricks.spark.csv")
      .mode("overwrite").csv("/Users/ravi/ScalaProjects/enrich/data/results/sales.csv")

    }
}
