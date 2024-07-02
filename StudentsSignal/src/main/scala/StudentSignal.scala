import scala.io.Source
import scala.util.Random
import upickle.default._
import upickle.default.{ReadWriter => RW, macroRW}

case class StudentSignal(
                          login: String,
                          latitude: Double,
                          longitude: Double,
                          exercise: Int,
                          score: Int,
                          timestamp: Long
                        )
object StudentSignal{
  implicit val rw: RW[StudentSignal] = macroRW
}

object Tools
{

  private def readLinesFromFile(fileName: String): List[String] = {
    val source = Source.fromFile(fileName)
    val lines = try source.getLines().toList finally source.close()
    lines
  }

  private def getRandomName(list: List[String]): String = {
    val randomIndex = Random.nextInt(list.length)
    list(randomIndex)
  }

  private def getRandomElement(value: ujson.Value) : ujson.Value = {
    val list = value.arr
    val randomIndex = Random.nextInt(list.length)
    list(randomIndex)
  }

  private def createLogin() : String = {
    val firstNames = readLinesFromFile("src/main/resources/firstname.txt")
    val lastNames = readLinesFromFile("src/main/resources/lastname.txt")

    val randomFirstName = getRandomName(firstNames)
    val randomLastName = getRandomName(lastNames)

    s"$randomFirstName.$randomLastName"
  }

  private def createCoords() : (Int, Double, Double) = {
    val source = Source.fromFile("src/main/resources/coords.json")
    val jsonString  = source.mkString
    source.close()

    val json = ujson.read(jsonString)
    val regions = json("regions")

    val randomRegion = getRandomElement(regions)

    val randomCity = getRandomElement(randomRegion("cities"))

    val latitude = randomCity("latitude").num
    val longitude = randomCity("longitude").num

    (randomRegion("region_id").num.toInt,  latitude, longitude)
  }

  private def createExercise(): Int = {
    Random.nextInt(118) // There are 118 exercises
  }

  private def easyWeighted(value: Int): Int = {
    (Math.pow(value / 100.0, 1 / 2.0) * 100).toInt
  }

  private def mediumWeighted(value: Int): Int = {
    value
  }

  private def hardWeighted(value: Int): Int = {
    (Math.pow(value / 100.0, 2) * 100).toInt
  }

  private def createScoreFromDifficulty(difficulty: String) : Int = difficulty match {
    case "easy" => easyWeighted(Random.nextInt(101)) // tends to 100
    case "medium" => mediumWeighted(Random.nextInt(101)) // uniform
    case "hard" => hardWeighted(Random.nextInt(101)) // tends to 0
  }

  private def createScoreFromRegion(score: Int, regionId: Int): Int = regionId match {
    case 3 | 5 | 6 | 7 => easyWeighted(score)
    case 1 | 2 | 4 | 8 => mediumWeighted(score)
    case 9 | 10 | 11 | 12 => hardWeighted(score)
  }

  private def createScore(exercise: Int, regionId: Int): Int = {
    val source = Source.fromFile("src/main/resources/exercises.json")
    val jsonString  = source.mkString
    source.close()

    val json = ujson.read(jsonString)
    val exercises = json("exercises")

    val list = exercises.arr
    val exercise_info = list(exercise)

    val difficulty = exercise_info("difficulty")

    val scoreBeforeRegion = createScoreFromDifficulty(difficulty.str)

    createScoreFromRegion(scoreBeforeRegion, regionId)
  }

   def createStudentSignal(timestamp: Long): StudentSignal = {
    val login = createLogin()
    val (regionId, latitude, longitude) = createCoords()

    val exercise = createExercise()
    val score = createScore(exercise, regionId)

    StudentSignal(login, latitude, longitude, exercise + 1, score, timestamp)
  }
}
