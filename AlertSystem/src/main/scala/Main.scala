object Main {
  def main(args: Array[String]): Unit = {
    val system: AlertSystem = new AlertSystem()

    Console.println("System is ready to filter messages!")
    Console.println("Env KAFKA_IN_HOST = " + sys.env.getOrElse("KAFKA_IN_HOST", "localhost"))
    Console.println("Env KAFKA_IN_PORT = " + sys.env.getOrElse("KAFKA_IN_PORT", "9092"))
    Console.println("Env KAFKA_OUT_HOST = " + sys.env.getOrElse("KAFKA_OUT_HOST", "localhost"))
    Console.println("Env KAFKA_OUT_PORT = " + sys.env.getOrElse("KAFKA_OUT_PORT", "9093"))
    system.run()
  }
}