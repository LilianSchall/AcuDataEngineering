import org.apache.hadoop.conf.{Configuration}
import org.apache.hadoop.fs.{FileSystem, FileStatus, Path}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.Properties
import java.net.URI

class Analyzer {
  // Setup spark session
  private val spark = SparkSession.builder()
    .appName("Student report analyzer")
    .getOrCreate()


  // Setup retrieval of files from hdfs
  private val hdfsFilePattern = "/topics/student_report/partition=0/*.json"
  private val hadoopConfig = new Configuration()
  private val hdfs = FileSystem.get(new URI("hdfs://localhost:8020"), hadoopConfig)
  private val filePatternPath = new Path(hdfsFilePattern)

  private val fileStatuses: Array[FileStatus] = hdfs.globStatus(filePatternPath)
  private val filePaths = fileStatuses.map(_.getPath.toString)

  private val studentDF: DataFrame = spark.read
    .option("header", "true")
    .json(filePaths: _*)

  // Setup connection to database
  private val jdbcUrl = "jdbc:postgresql://analytics-db:5432/analytics"
  private val dbProperties = new Properties()

  dbProperties.setProperty("user", "analytics_service")
  dbProperties.setProperty("password", sys.env.getOrElse("DB_PASSWORD", "analytics123"))
  dbProperties.setProperty("driver", "org.postgresql.Driver")

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
    ???

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
