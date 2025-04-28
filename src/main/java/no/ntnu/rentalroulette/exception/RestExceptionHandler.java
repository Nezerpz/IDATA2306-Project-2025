package no.ntnu.rentalroulette.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(ExpiredJwtException.class)
  protected ResponseEntity<String> handleJwtTokenExpiredException(ExpiredJwtException e) {
    return new ResponseEntity<>("Your token has expired, please login again.",
        HttpStatus.UNAUTHORIZED);
  }
}