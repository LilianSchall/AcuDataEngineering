import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer, ConsumerRecords}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import upickle.default._
import upickle.default.{ReadWriter => RW, macroRW}

import java.util.Properties
import scala.annotation.tailrec
import scala.collection.JavaConverters._

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

class AlertSystem {
  private val consumerProps: Properties = new Properties()
  consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
    sys.env.getOrElse("KAFKA_IN_HOST", "localhost") + ":" +
      sys.env.getOrElse("KAFKA_IN_PORT", "9092"))
  consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, sys.env.getOrElse("KAFKA_IN_CONSUMER_GROUP", "alert-consumer-group"))

  private val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProps)

  private val producerProps: Properties = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
    sys.env.getOrElse("KAFKA_OUT_HOST", "localhost") + ":" +
      sys.env.getOrElse("KAFKA_OUT_PORT", "9093"))
  producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](producerProps)

  consumer.subscribe(List("student_report").asJava)

  @tailrec
  final def run(): Unit = {
    val records: ConsumerRecords[String, String] = consumer.poll(java.time.Duration.ofMillis(100))
    records.asScala.foreach { record =>
        val value = record.value()
        val studentSignal = read[StudentSignal](value)
        // TODO: ajouter fonction qui check seuil de difficulté exercice et lance alerte en conséquence
        val floor = sys.env.getOrElse("SCORE_FLOOR", "20").toInt
        if (studentSignal.score <= floor) {
          val alertString = write(studentSignal)
          val producerRecord : ProducerRecord[String, String] = new ProducerRecord[String, String]("alert", "report", alertString)
          producer.send(producerRecord).get()
        }
    }
    run()
  }
}
