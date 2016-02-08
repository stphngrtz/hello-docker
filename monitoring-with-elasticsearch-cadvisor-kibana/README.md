# Docker Monitoring with cAdvisor, Elasticsearch, Kibana

https://github.com/google/cadvisor/issues/634
https://github.com/google/cadvisor/pull/875

```
safari http://192.168.99.100:8080 (cadvisor)
safari http://192.168.99.100:5601 (kibana)
```

Issue:
- [cadvisor.go:68] Failed to connect to database: failed to create the elasticsearch client - no Elasticsearch node available >>> SOLVED by sleep
- Docker 1.10 Issues:
  - https://github.com/google/cadvisor/issues/1090
  - https://github.com/google/cadvisor/issues/1091

Todos:
- Dashboards (https://github.com/gregbkr/docker-elk-cadvisor-dashboards)
