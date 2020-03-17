# Homework
`git clone https://gitlab.com/pigorv/spring-cloud-overview`

`cd spring-cloud-overview`

`git checkout homework`

Add the project to your repository with changes written below.

## Notification server
* Run service at `localhost:8484`
* Notification service should be registered in Eureka Server with `notifications` service id
* Add route to API Gateway. `localhost:8080/notifications/**` should be routed to `notfication` service running at port `8484` 
* Add Spring MVC annotations and some logic to `NotificationController.class` to handle GET and POST request.
    * POST - adds Notification for given user to list  
    * GET - returns collection of notifications  

## Order service
* Rewrite `OrdersController.createNewOrder()` to use `FeignClient's` instead of `RestTeamplate`.
* Add POST HTTP call in `OrdersController.createNewOrder()` to `notification` service using `FeignClient`

## Success criteria
As a homework-checker I should:

* Run services: `eureka-server`, `notification`, `orders`, `products`, `users` and `gateway`

* Create user 
``` bash
curl -X POST http://localhost:8080/users
```
* Create product 
``` bash
curl -X POST http://localhost:8080/products
```
* Create order 
``` bash
curl --url http://localhost:8080/orders \
     -H "Content-Type: application/json" \
     -d '{"userName": "{сreatedUserName}", "product": "{сreatedProductName}"}'
```
* `OrdersController.class` in `orders` module should have no `RestTamplate` and should user Feign interfaces to make HTTP calls.

* Check is `notification` service have handled POST request
``` bash
curl -X GET http://localhost:8080/notifications
Response should look like: [{"user":"{сreatedUserName}","notifyBy":"EMAIL"}]%  
```

 * Call `notification` service directly without API Gateway should responds with the same result.
``` bash
curl -X GET http://localhost:8484/
Response should look like: [{"user":"{сreatedUserName}","notifyBy":"EMAIL"}]%  
```