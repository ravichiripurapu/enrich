package data.pipeline.components

import data.pipeline.api.components.AbstractComponent
import org.apache.spark.sql.{DataFrame, SparkSession}

class CustomerComponent extends AbstractComponent {

  // Method to get input classes
  override def getInputClasses: Array[Class[_]] = Array()

  // Method to get output classes
  override def getOutputClasses: Array[Class[_]] = Array(classOf[DataFrame])

  // Name of the component
  override def getName: String = "FilterComponent"

  // Overriding the executeComponent method
  override protected def executeComponent(): Unit = {
    println("FilterComponent")
    println(this.getEnvironmentKey)
    println(this.getNodeConfiguration)


    this.getEnvironmentProperties()

    val environmentProperties = this.getEnvironmentProperties()
    val spark = environmentProperties.getProperties().get("spark").asInstanceOf[SparkSession]

    val inputFile = "/Users/ravi/ScalaProjects/enrich/data/customer/customers.csv"

    val df: DataFrame = spark.read.option("header", "true").csv(inputFile)

    this.setOutputValue(0, df)
  }

}
