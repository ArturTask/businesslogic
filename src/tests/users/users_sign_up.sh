#!bin/bash

curl -X POST \
  -H "Content-Type:application/json" \
  -d '{"login": "333", "password": "333", "email":"ssss@mail.ru"}' \
  http://localhost:8080/user/reg