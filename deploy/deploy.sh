#!/bin/bash

USER=${1:-274007}
PORT=${2:-17499}
CONFIG=helios.properties
if [ -f $CONFIG ]; then
  mvn -f ../pom.xml package
  FILES=(../target/*.jar)
  JAR=$(basename "${FILES[0]}")
  mv ../target/"${JAR}" ./
  echo "java18 -Dspring.config.location=helios.properties -jar ${JAR}" >> start.sh
  scp -P 2222 ./* s"${USER}"@se.ifmo.ru:~/
  rm "$JAR" start.sh
  ssh -p 2222 s"$USER"@se.ifmo.ru -L "$PORT":localhost:"$PORT" # должно быть активно
else
  echo "Create $CONFIG before deploying"
fi

#ssh -p 2222 sXXXXXX@se.ifmo.ru -L31440:helios:31440