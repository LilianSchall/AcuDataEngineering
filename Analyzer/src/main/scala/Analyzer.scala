import org.apache.hadoop.conf.{Configuration}
import org.apache.hadoop.fs.{FileSystem, FileStatus, Path}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.util.Properties

class Analyzer {
  // Setup spark session
  private val spark = SparkSession.builder()
    .appName("Student report analyzer")
    .getOrCreate()


  // Setup retrieval of files from hdfs
  private val hdfsFilePattern = "hdfs://hdfs-datanode/students/report_*.csv"
  private val hadoopConfig = new Configuration()
  private val hdfs = FileSystem.get(hadoopConfig)
  private val filePatternPath = new Path(hdfsFilePattern)

  private val fileStatuses: Array[FileStatus] = hdfs.globStatus(filePatternPath)
  private val filePaths = fileStatuses.map(_.getPath.toString)

  private val studentDF: DataFrame = spark.read
    .option("header", "true")
    .csv(filePaths: _*)

  // Setup connection to database
  private val jdbcUrl = "jdbc:postgresql://analytics-db:5432/analytics"
  private val dbProperties = new Properties()

  dbProperties.setProperty("user", "analytics")
  dbProperties.setProperty("password", "kafka")
  dbProperties.setProperty("driver", "org.postgresql.Driver")

  // Example for writing a dataframe in the db
  /* studentDF.write
      .mode("overwrite")
      .jdbc(jdbcUrl, "table_name", dbProperties)
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