package com.epam.springcloud.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not found enough quantity for the product")
public class NotFoundProductException extends RuntimeException {
}
