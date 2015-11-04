# Networking containers
http://docs.docker.com/engine/userguide/networkingcontainers/

### Create your own bridge network
```
docker network create -d bridge my-bridge-network
```

## Add containers to a network
```
docker run -d --net=my-bridge-network --name db training/postgres
docker run -d --name web training/webapp python app.py
docker inspect web

docker exec -it db bash
ping 172.17.0.2

docker network connect my-bridge-network web
docker exec -it db bash
ping web
```
