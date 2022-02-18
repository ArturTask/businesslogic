#!bin/bash

curl -X POST \
  -H "Content-Type:application/json" \
  -d '{"login": "3", "password": "3", "email":"ssss@mail.ru"}' \
  http://localhost:17499/user/reg