#!/usr/bin/env bash

CONTAINER_ID=$(docker run -d -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:9.6)

if ! docker top $CONTAINER_ID &>/dev/null
then
    echo "Unable to start container."
    exit 1
fi

echo "Running integration tests against $CONTAINER_ID."

DATABASE_CONNECTION_STRING=jdbc:postgresql://postgres:password@localhost:5432/postgres \
PORT=18080 \
MIGRATE_DATABASE_AT_STARTUP=true \
USE_IN_MEMORY_DATABASE=false \
./gradlew clean integrationTest \
-Dorg.gradle.parallel=false # this is important because the integration tests MUST NOT run in parallel

docker rm -f $CONTAINER_ID > /dev/nul