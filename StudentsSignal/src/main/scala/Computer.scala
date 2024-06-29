import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import upickle.default._
import Tools._
import StudentSignal._

import scala.annotation.tailrec

class Computer {
  private val props: Properties = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
    sys.env.getOrElse("KAFKA_IN_HOST", "localhost") + ":" +
      sys.env.getOrElse("KAFKA_IN_PORT", "9092"))
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

  /*
   * This method should create an infinite amount of data via list generation.
   * On this list should be applied a foreach that sends the data into the kafka stream.
   * The data should be random but should follow some rules for certains attributes:
   * - The percentage should be between 0 and 100%.
   * - The name of the people should be generated by combining a first name and last name from two files (coming shortly).
   * - The timestamp (in s) should be incremented by the number of times we submitted a data into the kafka stream.
   */
  @tailrec
  final def flood(timestamp : Long = System.currentTimeMillis() / 1000): Unit = {

    val value = write(createStudentSignal(timestamp))

    Console.println(value)

    val record = new ProducerRecord[String, String]("student_report", "report", value)
    producer.send(record).get()

    flood(timestamp + 1)
  }
}
