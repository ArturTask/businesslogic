#!bin/bash

curl -X PUT \
  -H "Content-Type:application/json" \
  -d '{"creatorId": "7", "head": "2", "body": "ddddd", "evaluated": false, "tag": "JAVA", "token":"98f13708210194c475687be6106a3b84"}' \
  http://localhost:8080/questions/add
