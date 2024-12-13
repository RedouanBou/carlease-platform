package com.nl.sogeti.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND, reason = "User already exists")
public class UserAlreadyExists extends RuntimeException {

}