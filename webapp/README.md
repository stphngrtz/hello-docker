# Run a simple application
http://docs.docker.com/engine/userguide/usingdocker/

## Viewing our web application container
```
docker run -d -P training/webapp python app.py
docker ps -l
docker ps -a
docker-machine ip default
safari http://192.168.99.100:32768
```

## A network port shortcut
```
docker port suspicious_fermi
docker port suspicious_fermi 5000
```

## Viewing the web application’s logs
```
docker logs -f suspicious_fermi
```

## Looking at our web application container’s processes
```
docker top suspicious_fermi
```

## Inspecting our web application container
```
docker inspect suspicious_fermi | less
```

## Stopping our web application container
```
docker stop suspicious_fermi
```

## Restarting our web application container
```
docker start suspicious_fermi
```

## Removing our web application container
```
docker rm suspicious_fermi
```
