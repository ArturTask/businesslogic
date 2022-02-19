#!bin/bash

curl -X POST \
  -H "Content-Type:application/json" \
  -d '{"login": "1", "password": "1", "email":"ssss@mail.ru"}' \
  http://localhost:8080/user/reg