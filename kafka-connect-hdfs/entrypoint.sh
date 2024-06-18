#!/bin/bash

/etc/confluent/docker/run &

while ! curl -s localhost:8083; do
    echo "Waiting for Kafka connect to start..."
    sleep 5
done

curl -X POST -H "Content-Type: application/json" --data @/etc/kafka-connect/hdfs-sink-connector.json http://localhost:8083/connectors

wait
