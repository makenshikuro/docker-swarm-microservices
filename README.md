# docker-swarm-microservices
Final project for Cloud-Computing. Microservice architecture using docker machine, compose and swarm.

## Enviorment variables (SO) and openstack
`export OS_DOMAIN_NAME=<Domain Name>
export OS_TENANT_NAME=<Project Name>
export OS_SSH_USER =<ssh user>`

`source openstack_cli.rc`

## Docker

`docker-machine  create --openstack-flavor-name <flavour> --openstack-image-name <image name> --openstack-net-name <net name> --openstack-tenant-name <project> --openstack-floatingip-pool <floating IP pool>  --driver openstack --openstack-ssh-user <user>  <machine name>`

`docker-machine ssh <machine name>`
`docker swarm init --advertise-addr x.y.z.w`
`sudo usermod -aG docker <user>`

`docker network create --subnet 10.200.0.0/24 p3-net`


## login (Java - Spring Boot) 8080
Spring Boot microservice to authenticate users.
Endpoints: 
- POST /login
- POST /register
User = {
    long id
    String user
    String password
    String rol
}
Includes the response from /login endpoint in 'Authorization' header with this format: 'Bearer :tokenGivenFromLogin';
It connects to the MySQL service.
Build imager:
docker build -t login .
Run Container:
`docker run --name login --network p3-net -p 8080:8080 -v $(pwd)/application.properties:/service/application.properties  twcammaster.uv.es/auth_g1`

## counter (Java - Spring Boot) 8081
Spring Boot microservice to register likes in pictures.
- POST /increment/{var}
- POST /decrement/{var}
It connects to the Redis service.
Build imager:
docker build -t counter .
Build Container:
`docker run --name contador  -p 8081:8081 -v $(pwd)/application.properties:/service/application.properties  --network p3-net twcammaster.uv.es/contadores_g1`


## fotos (Java - Spring Boot) 8082
Microservice developed with Java and MongoDB that connects to the MongoDB service and Swift service.
Endpoints: 
- POST /photo (multipart)
- GET /photo

Build imager:
docker build -t fotos .
Build Container:
`docker run --name fotos -p 8082:8082  fotos`

## Redis (Dockerized)
Redis service on port 6379
`docker run --name redis -p 6379:6379 --network p3-net redis`

## MongoDB (Dockerized) 27017
MongoDB service on port 27017
`docker run -d -p 27017:27017 --name mongo --network p3-net mongo`

## MySQL service (Dockerized) 3306
MySQL service running on port 3306.
`docker run --name mysql -e MYSQL_DATABASE=login -e MYSQL_USER=login -e MYSQL_PASSWORD=loginpw -e MYSQL_ALLOW_EMPTY_PASSWORD=no -p 3306:3306 --network p3-net mysql`

## Redis service (Dockerized) 6379
Redis service running on port 6379.
`docker run --name redis -p 6379:6379 -d redis`

## Swift service (Dockerized) 8080
Swift service running on port 8080
`docker run -d -p 8083:8080 --name swift --network p3-net twcammaster.uv.es/swift`

## Docker machine

`eval $(docker-machine env <machine with docker-swarm>)`
`docker stack deploy --compose-file docker-cloud.yml webapp`
    
## Requests Examples

### login microservice

`curl -H "Content-Type: application/json" -d '{"user":"b","password":"b"}' http://x.y.z.w:8080/register`
`curl -H "Content-Type: application/login" -d '{"user":"b","password":"b"}' http://x.y.z.w:8080/login`

### counters microservice

`curl --request GET \
--url http://x.y.z.w:8081/get/oscar-titulo1 \
--header 'authorization: Bearer <JWT>'`

### fotos microservice
Método POST
`curl \
--header 'authorization: Bearer <JWT>' \
  -F "title=titulo1" \
  -F "description=This is an image file" \
  -F "file=@/aa.png" \
  http://x.y.z.w:8082/fotos`

Método GET
`curl \
--url http://x.y.z.w:8082/fotos \
--header 'authorization: Bearer <JWT>'`


