FROM confluentinc/cp-kafka-connect:7.6.1

RUN confluent-hub install --no-prompt confluentinc/kafka-connect-replicator:latest

ADD kafka-alert-connector.json /etc/kafka-connect/kafka-alert-connector.json

COPY entrypoint.sh /etc/kafka-connect/entrypoint.sh

ENTRYPOINT ["/etc/kafka-connect/entrypoint.sh"]
