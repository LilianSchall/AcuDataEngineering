import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import upickle.default._

import java.util.Properties
import scala.annotation.tailrec

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


  @tailrec
  final def run(): Unit = {
    # consumer.get pour recup chaine de caractères
    # serialiser avec upickle dans case class
    # regarder score et evaluer selon seuil fixe
    # seuil en variable d'environnement
    # si score <= seuil, producer.send avec la string
    # si score > seuil, rien à faire et appel recursif passe au message suivant
    # push une fois que ça marche
    # ensuite, faire un seuil en fonction de la difficulté
    val records = consumer.poll(java.time.Duration.ofMillis(100)).asScala

    for (record <- records) {
      try {
        val jsonString = record.value()
        
        val studentSignal = read[StudentSignal](jsonString)
        
        val floor = sys.env.getOrElse("SCORE_FLOOR", "20").toInt
        if (studentSignal.score <= floor) {

          val alertString = write(studentSignal)
          val producerRecord = new ProducerRecord[String, String](alert, AlertString)
          producer.send(producerRecord)
        }
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
    run()
  }
}
