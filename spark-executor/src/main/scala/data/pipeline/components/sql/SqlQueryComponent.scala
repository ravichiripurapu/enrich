package data.pipeline.components.sql

import data.pipeline.api.components.AbstractComponent
import org.apache.spark.sql.{DataFrame, SparkSession}

class SqlQueryComponent extends AbstractComponent {

  // Method to get input classes
  override def getInputClasses: Array[Class[_]] = Array(classOf[DataFrame],classOf[DataFrame])

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

    val df1: DataFrame = this.getInputValue(0).asInstanceOf[DataFrame]
    val df2: DataFrame = this.getInputValue(1).asInstanceOf[DataFrame]

    df1.createOrReplaceTempView("customers")

    df2.createOrReplaceTempView("sales")

    val df3: DataFrame = spark.sql("select c.id, c.customer_name, s.sales_amount from " +
      " customers c, sales s where c.id = s.customer_id ")

    df3.show()

    this.setOutputValue(0, df3)
  }

}
