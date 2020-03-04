# Spring Cloud overview

## Product service
`cd products` then run `mvn spring-boot:run`

### Use Product service:
``` bash
curl -X POST http://localhost:8181/
```
``` bash
curl -X GET http://localhost:8181/{сreatedProductName}
```
Get all products:
``` bash
curl -X GET http://localhost:8181/
```
Decrease quantity of product by 1:
``` bash
curl -X PUT http://localhost:8181/{сreatedProductName}
```

## User service
`cd users` then run `mvn spring-boot:run`

### Use User service:
``` bash
curl -X POST http://localhost:8383/
```
``` bash
curl -X GET http://localhost:8383/{сreatedUserName}
```
Get user's products:
``` bash
curl -X GET GET http://localhost:8383/{сreatedUserName}/products
```

## Order service
`cd orders` then run `mvn spring-boot:run`

### Use User service:
``` bash
curl --url http://localhost:8282/ \
     -H "Content-Type: application/json" \
     -d '{"userName": "{сreatedUserName}", "product": "{сreatedProductName}"}'
```
Get user's products:
``` bash
curl -X GET http://localhost:8282/users/{сreatedUserName}
```