# Docker with java microservice and postgresql database
* microservice built with sparkjava
* fatjar built with maven-shade-plugin
* postgres db
* Dockerfiles
* linked containers

```
cd db/
docker build -t hello-docker-java-with-database-db .
docker run --name db -e POSTGRES_USER=stephan -e POSTGRES_PASSWORD=mysecretpassword -d hello-docker-java-with-database-db

cd ..

cd app/
mvn clean package
docker build -t hello-docker-java-with-database-app .
docker run --name app --link db:db -d -p 4567:4567 hello-docker-java-with-database-app

safari http://192.168.99.100:4567
```
