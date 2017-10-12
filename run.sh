#!/usr/bin/env bash
apt-get -qq update
apt-get install -y mysql-client
result=$(mysqladmin ping -hmysql -u Admin -pAdmin1234)
while [[ $result == *"failed"* ]]; do
    sleep 1s
    result=$(mysqladmin ping -hmysql -u Admin -pAdmin1234)
    echo "Wait for mysql DB"
done

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar if-behappy-backend-1.0.jar