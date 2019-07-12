#!/bin/sh
mvn clean package && docker build -t aem.example.jee/JaxRsApi .
docker rm -f JaxRsApi || true && docker run -d -p 8080:8080 -p 4848:4848 --name JaxRsApi aem.example.jee/JaxRsApi 
