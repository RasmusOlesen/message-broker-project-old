# message-broker-project
Message Broker Project notes and guides.

## Docker Compose
https://docs.docker.com/engine/install/ubuntu/

### RabbitMQ
https://registry.hub.docker.com/_/rabbitmq
```bash
$ cd docker-compose
$ sudo docker-compose -f rabbitmq.yaml up
```
http://localhost:15672/ (guest:guest)

### PostgreSQL
https://registry.hub.docker.com/_/postgres
```bash
$ cd docker-compose
$ sudo docker-compose -f postgresql.yaml up
```
jdbc:postgresql://localhost:5432/postgres (postgres:examlpe)

