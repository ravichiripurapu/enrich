package data.pipeline.components.file

import com.google.gson.Gson
import data.pipeline.api.components.AbstractComponent
import data.pipeline.components.model.FileConfig
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.SaveMode

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


    val gson = new Gson()
    val fileConfig = gson.fromJson(this.getNodeConfiguration, classOf[FileConfig])

    val filePath = fileConfig.getFileName

    val fileFormat = fileConfig.getFileType

    val isHeader = fileConfig.isHeader.toString

    val isInferSchema = fileConfig.isInferSchema.toString

    this.getEnvironmentProperties()

    val environmentProperties = this.getEnvironmentProperties()
    val spark = environmentProperties.getProperties().get("spark").asInstanceOf[SparkSession]

    val csvOptions = Map("header" -> isHeader, "inferSchema" -> isInferSchema)

    val df1: DataFrame = this.getInputValue(0).asInstanceOf[DataFrame]

    df1.show()

    writeToFile(df1, filePath, fileFormat)

  }

  def writeToFile(df: DataFrame, filePath: String, fileFormat: String, mode: SaveMode = SaveMode.Overwrite, options: Map[String, String] = Map()): Unit = {

    fileFormat.toLowerCase match {
      case "csv" =>
        // Writing to CSV format
        df.write
          .format("csv")
          .options(options)
          .mode(mode)
          .csv(filePath)
        println(s"Data written to CSV at: $filePath")

      case "parquet" =>
        // Writing to Parquet format
        df.write
          .format("parquet")
          .options(options)
          .mode(mode)
          .parquet(filePath)
        println(s"Data written to Parquet at: $filePath")

      case "json" =>
        // Writing to JSON format
        df.write
          .format("json")
          .options(options)
          .mode(mode)
          .json(filePath)
        println(s"Data written to JSON at: $filePath")

      case "iceberg" =>
        // Writing to Iceberg format (ensure Iceberg is configured and added to the dependencies)
        df.write
          .format("iceberg")
          .options(options)
          .mode(mode)
          .save(filePath)
        println(s"Data written to Iceberg at: $filePath")

      case _ =>
        throw new IllegalArgumentException(s"Unsupported file format: $fileFormat")
    }
  }

}
