#!bin/bash

curl -X DELETE \
  -H "Content-Type:application/json" \
  -d '{"id":4, "token":"98f13708210194c475687be6106a3b84"}' \
  http://localhost:8080/questions/delete