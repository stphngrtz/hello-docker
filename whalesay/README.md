# Getting started
http://docs.docker.com/mac/started/

```
docker build -t docker-whale .
docker images
docker run docker-whale

docker tag 7784cd9d8aac stphngrtz/docker-whale:latest
docker login --username=stphngrtz --password=***** --email=*****
cat /Users/stephan/.docker/config.json
docker push stphngrtz/docker-whale

docker rmi -f docker-whale
docker rmi -f 7784cd9d8aac

docker pull stphngrtz/docker-whale
docker run stphngrtz/docker-whale
```
