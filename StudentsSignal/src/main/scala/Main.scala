object Main {
  def main(args: Array[String]): Unit = {
    val computer: Computer = new Computer()

    Console.println("Computer is ready to flood!")
    Console.println("Env KAFKA_IN_HOST = " + sys.env.getOrElse("KAFKA_IN_HOST", "localhost"))
    Console.println("Env KAFKA_IN_PORT = " + sys.env.getOrElse("KAFKA_IN_PORT", "9092"))
    computer.flood()
  }
}