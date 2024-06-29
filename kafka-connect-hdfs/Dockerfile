FROM confluentinc/cp-kafka-connect:7.6.1

RUN confluent-hub install --no-prompt confluentinc/kafka-connect-hdfs3:latest

ADD hdfs-sink-connector.json /etc/kafka-connect/hdfs-sink-connector.json

COPY entrypoint.sh /etc/kafka-connect/entrypoint.sh

ENTRYPOINT ["/etc/kafka-connect/entrypoint.sh"]
