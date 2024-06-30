import java.sql.{Connection, DriverManager, ResultSet}

object DatabaseUtils {
  val dbName: String = sys.env.getOrElse("DB_NAME", "postgres")
  val dbHost: String = sys.env.getOrElse("DB_HOST", "localhost")
  val dbPort: String = sys.env.getOrElse("DB_PORT", "5432")
  val user: String = sys.env.getOrElse("DB_USER", "user")
  val password: String = sys.env.getOrElse("DB_PASSWORD", "password")

  val url: String = s"jdbc:postgresql://$dbHost:$dbPort/$dbName"

  def getConnection(): Connection = {
    DriverManager.getConnection(url, user, password)
  }

  def getExerciseDifficulties(): Map[Int, Int] = {
    val connection = getConnection()
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT id, difficulty FROM exercises")

    val difficulties = Iterator.continually(resultSet).takeWhile(_.next()).map { rs =>
      rs.getInt("id") -> rs.getInt("difficulty")
    }.toMap

    connection.close()
    difficulties
  }
}