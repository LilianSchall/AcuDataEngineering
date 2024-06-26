import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import java.util.Properties
import scala.annotation.tailrec

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
    ???
    run()
  }
}
