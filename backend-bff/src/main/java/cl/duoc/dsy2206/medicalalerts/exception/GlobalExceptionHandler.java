package cl.duoc.dsy2206.medicalalerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(ResourceNotFoundException.class)
 @ResponseStatus(HttpStatus.NOT_FOUND)
 public ProblemDetail handleResourceNotFound(ResourceNotFoundException exception) {
  ProblemDetail problemDetail =
    ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
  problemDetail.setTitle("Resource not found");
  return problemDetail;
 }

 @ExceptionHandler(IllegalArgumentException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public ProblemDetail handleIllegalArgument(IllegalArgumentException exception) {
  ProblemDetail problemDetail =
    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

  problemDetail.setTitle("Invalid request");
  return problemDetail;
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
  String message = exception.getBindingResult().getFieldErrors().stream().findFirst()
    .map(error -> error.getField() + ": " + error.getDefaultMessage()).orElse("Validation error");

  ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
  problemDetail.setTitle("Validation error");
  return problemDetail;
 }
}
