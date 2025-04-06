package data.pipeline.components.file

import com.google.gson.Gson
import data.pipeline.api.components.AbstractComponent
import data.pipeline.components.model.{FileConfig, QueryConfig}
import org.apache.spark.sql.{DataFrame, SparkSession}

class FileReaderComponent extends AbstractComponent {

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

    val gson = new Gson()
    val fileConfig = gson.fromJson(this.getNodeConfiguration, classOf[FileConfig])

    val inputFile = fileConfig.getFileName

    val fileFormat = fileConfig.getFileType

    val isHeader = fileConfig.isHeader.toString

    val isInferSchema = fileConfig.isInferSchema.toString

    this.getEnvironmentProperties()

    val environmentProperties = this.getEnvironmentProperties()
    val spark = environmentProperties.getProperties().get("spark").asInstanceOf[SparkSession]

    val csvOptions = Map("header" -> isHeader, "inferSchema" -> isInferSchema)

    val df: DataFrame = readFile(spark, inputFile, fileFormat, csvOptions)

    this.setOutputValue(0, df)
  }

  def readFile(sparkSession: SparkSession, filePath: String, fileFormat: String, options: Map[String, String] = Map()): DataFrame = {
    try {
      // Handle different file formats
      fileFormat.toLowerCase match {
        case "csv" =>
          sparkSession.read.options(options).csv(filePath)
        case "json" =>
          sparkSession.read.options(options).json(filePath)
        case "parquet" =>
          sparkSession.read.options(options).parquet(filePath)
        case "avro" =>
          sparkSession.read.format("avro").options(options).load(filePath)
        case "text" =>
          sparkSession.read.text(filePath)
        case _ =>
          throw new IllegalArgumentException(s"Unsupported file format: $fileFormat")
      }
    } catch {
      case e: Exception =>
        println(s"Error reading file: ${e.getMessage}")
        throw e
    }
  }

}
