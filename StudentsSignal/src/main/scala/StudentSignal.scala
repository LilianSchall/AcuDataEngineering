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
                        )
object StudentSignal{
  implicit val rw: RW[StudentSignal] = macroRW
}

object Tools
{

  def readLinesFromFile(fileName: String): List[String] = {
    val source = Source.fromFile(fileName)
    val lines = try source.getLines().toList finally source.close()
    lines
  }

  def getRandomName(list: List[String]): String = {
    val randomIndex = Random.nextInt(list.length)
    list(randomIndex)
  }

  def getRandomElement(value: ujson.Value) : ujson.Value = {
    val list = value.arr
    val randomIndex = Random.nextInt(list.length)
    list(randomIndex)
  }

  def createLogin() : String = {
    val firstNames = readLinesFromFile("src/main/resources/firstname.txt")
    val lastNames = readLinesFromFile("src/main/resources/lastname.txt")

    val randomFirstName = getRandomName(firstNames)
    val randomLastName = getRandomName(lastNames)

    s"$randomFirstName.$randomLastName"
  }

  def createCoords() : (Double, Double) = {
    val source = Source.fromFile("src/main/resources/coords.json")
    val jsonString  = source.mkString
    source.close()

    val json = ujson.read(jsonString)
    val regions = json("regions")

    val randomRegion = getRandomElement(regions)

    val randomCity = getRandomElement(randomRegion("cities"))

    val latitude = randomCity("latitude").num
    val longitude = randomCity("longitude").num

    (latitude, longitude)
  }

  def createExercise(): Int = {
    Random.nextInt(120)
  }

  def createScore(exercise: Int): Int = {
    Random.nextInt(100)
  }

   def createStudentSignal(): StudentSignal = {
    val login = createLogin()
    val (latitude, longitude) = createCoords()

    val exercise = createExercise()
    val score = createScore(exercise)

    StudentSignal(login, latitude, longitude, exercise, score)
  }
}
