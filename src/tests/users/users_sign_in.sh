#!bin/bash

curl -X POST \
  -H "Content-Type:application/json" \
  -d '{"login": "3", "password": "3"}' \
  http://localhost:8080/user/login