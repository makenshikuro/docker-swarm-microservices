# docker-swarm-microservices
Final project for Cloud-Computing. Microservice architecture using docker machine, compose and swarm.

## sb-login (Java - Spring Boot) 8080
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
docker build -t sb-login .
Run Container:
docker run --name sb-logincontainer -p 8080:8080  -e SECRET_TOKEN=secretlogin sb-login

## sb-counter (Java - Spring Boot) 8081
Spring Boot microservice to register likes in pictures.
Endpoints (see in sb-counter/api-swagger.yaml):
- POST /increment/{var}
- POST /decrement/{var}
It connects to the Redis service.
Build imager:
docker build -t sb-counter .
Build Container:
docker run --name countercontainer -p 8081:8080  sb-counter

## sb-photos (Java - Spring Boot) 8082
Microservice developed with Java and MongoDB that connects to the MongoDB service and Swift service.
Endpoints: 
- POST /photo (multipart)
- GET /photo

## MongoDB
MongoDB service

## MySQL service (Dockerized) 3306
MySQL service running on port 3306.
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root MYSQL_DATABASE=users -d mysql:5.7

## Redis service (Dockerized) 6379
Redis service running on port 6379.
`docker run --name redis -p 6379:6379 -d redis`

## Swift service (Dockerized) 8083
Swift service running on port 8083
`docker run -d -p 8080:8080 -v swift:/srv/node twcammaster.uv.es/swift`

## Docker machine

eval $(docker-machine env <machine with docker-swarm>)
docker stack deploy --compose-file docker-cloud.yml webapp
    
## Requests Examples

### login microservice

curl -H "Content-Type: application/json" -d '{"user":"b","password":"b"}' http://x.y.z.w:8080/register
curl -H "Content-Type: application/login" -d '{"user":"b","password":"b"}' http://x.y.z.w:8080/login

### counters microservice

curl --request GET \
--url http://x.y.z.w:8081/get/oscar-titulo1 \
--header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvc2NhciIsImlzcyI6IkZvdG9zIiwiZXhwIjoxNTQ3NzgwMjg2fQ.rskC_N8_RDoEl5_L6DlEHFbxVbB1tzaFA5S4LIONdXVvp7mBhVkLZuOEX1DeyQnswZkRGR1esqq0IAmG0Bw3mw'

### fotos microservice
Método POST
curl \
--header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkIiwiaXNzIjoiRm90b3MiLCJleHAiOjE1Mjg4MTQxODl9.bGHF9C_TbD8pT0d7QOmirySQqWlKJkVEKeMALikTIA5eKL49de86F' \
  -F "title=titulo1" \
  -F "description=This is an image file" \
  -F "file=@/aa.png" \
  http://x.y.z.w:8082/fotos

Método GET
curl \
--url http://x.y.z.w:8082/fotos \
--header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvc2NhciIsImlzcyI6IkZvdG9zIiwiZXhwIjoxNTQ3NzgwMjg2fQ.rskC_N8_RDoEl5_L6DlEHFbxVbB1tzaFA5S4LIONdXVvp7mBhVkLZuOEX1DeyQnswZkRGR1esqq0IAmG0Bw3mw'


