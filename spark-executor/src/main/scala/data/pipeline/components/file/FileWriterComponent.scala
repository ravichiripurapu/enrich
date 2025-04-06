package data.pipeline.components.file

import data.pipeline.api.components.AbstractComponent
import org.apache.spark.sql.DataFrame

class FileWriterComponent extends AbstractComponent {

  // Method to get input classes
  override def getInputClasses: Array[Class[_]] = Array(classOf[DataFrame])

  // Method to get output classes
  override def getOutputClasses: Array[Class[_]] = Array()

  // Name of the component
  override def getName: String = "FilterComponent"

  // Overriding the executeComponent method
  override protected def executeComponent(): Unit = {
    println("FilterComponent")
    println(this.getEnvironmentKey)
    println(this.getNodeConfiguration)


    this.getEnvironmentProperties()

    val environmentProperties = this.getEnvironmentProperties()

    val df1: DataFrame = this.getInputValue(0).asInstanceOf[DataFrame]

    df1.show()

    df1.
      write
      .format("com.databricks.spark.csv")
      .mode("overwrite").csv("/Users/ravi/ScalaProjects/enrich/data/results/output_results.csv")

  }

}
