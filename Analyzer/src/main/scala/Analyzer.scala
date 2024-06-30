import org.apache.hadoop.conf.{Configuration}
import org.apache.hadoop.fs.{FileSystem, FileStatus, Path}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{avg, col, count, when}
import org.apache.spark.SparkConf


import java.util.Properties
import java.net.URI

class Analyzer {
  private val conf: SparkConf = new SparkConf()
    .setAppName("Student report analyzer")

  // Setup spark session
  private val spark = SparkSession.builder()
    .config(conf)
    .getOrCreate()


  // Setup retrieval of files from hdfs
  private val hdfsFilePattern = "/topics/student_report/partition=0/*.json"
  private val hadoopConfig = new Configuration()
  private val hdfs = FileSystem.get(new URI("hdfs://hdfs-namenode:8020"), hadoopConfig)
  private val filePatternPath = new Path(hdfsFilePattern)

  private val fileStatuses: Array[FileStatus] = hdfs.globStatus(filePatternPath)
  private val filePaths = fileStatuses.map(_.getPath.toString)

  private val studentDF: DataFrame = spark.read
    .option("header", "true")
    .json(filePaths: _*)

  // Setup connection to database
  private val jdbcUrl = "jdbc:postgresql://analytics-db:5432/acu_infra"
  private val dbProperties = new Properties()

  dbProperties.setProperty("user", "analytics_service")
  dbProperties.setProperty("password", sys.env.getOrElse("DB_PASSWORD", "analytics123"))
  dbProperties.setProperty("driver", "org.postgresql.Driver")

  // Read cities database
  private val citiesDF: DataFrame = spark.read
    .jdbc(jdbcUrl, "cities", dbProperties)

  // Example for writing a dataframe in the db
  /* studentDF.write
      .mode("overwrite")
      .jdbc(jdbcUrl, "analytics", dbProperties)
   */

  /*
   * Process the studentDF dataframe and get analytics out of it.
   * Commit the different dataframes you may become into the postgresql database.
   */
  def run(): Unit = {

    val studentWithRegionDF = studentDF
      .join(citiesDF, Seq("latitude", "longitude"), "left")
      .select("login", "region_id", "exercise", "score")

    val averageScoreDF = studentWithRegionDF
      .groupBy("region_id", "exercise")
      .agg(
        avg("score").alias("average_score"),
        count(when(col("score") <= 20, true)).alias("nb_alert")
      )

    val finalDF = averageScoreDF
      .withColumnRenamed("exercise", "exercise_id")

    finalDF.write
      .mode("append")
      .jdbc(jdbcUrl, "analytics", dbProperties)

    // Delete all processed files
    fileStatuses.foreach(status => {
      val hdfsPath = status.getPath

      if (hdfs.exists(hdfsPath)) {
        hdfs.delete(hdfsPath, true)
      }
    })

    // Stop spark session
    spark.stop()
  }

}
