#!/bin/bash

while :
do
    echo "sleep for 60 seconds:";
    sleep 60;
    spark-submit --class Main --master spark://spark-master:7077 --deploy-mode client app.jar;
done
