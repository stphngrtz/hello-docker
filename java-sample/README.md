# Docker with java microservice
* microservice built with sparkjava
* fatjar built with maven-shade-plugin
* simple Dockerfile

```
docker build -t hello-docker-java-sample .
docker run -d -p 4567:4567 hello-docker-java-sample
safari http://192.168.99.100:4567
```
