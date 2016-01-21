# Docker Compose
https://docs.docker.com/compose/

* Dockerfiles
* docker-compose.yml
* Postgres
* sparkjava
* sql2o
* Flyway

```
mvn clean package -f ./service1/pom.xml
mvn clean package -f ./service2/pom.xml
mvn clean package -f ./service3/pom.xml

docker build -t hello-docker-compose-service1 ./service1

docker-compose up
CTRL+C

docker-compose up -d
docker-compose ps
docker-compose logs
docker-compose logs postgres1
docker-compose run hello-docker-compose-service1 env
docker-compose run hello-docker-compose-service2 env
docker-compose stop
docker-compose rm

safari http://192.168.99.100:4567/hello
safari http://192.168.99.100:4568/hello
safari http://192.168.99.100:4569/hello
safari http://192.168.99.100:4569/services
safari http://192.168.99.100:4569/discover
```
