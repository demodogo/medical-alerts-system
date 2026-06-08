package cl.duoc.dsy2206.clinical.exception;

import org.springframework.dao.DataIntegrityViolationException;
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

  problemDetail.setTitle("Recurso no encontrado");

  return problemDetail;
 }

 @ExceptionHandler(DataIntegrityViolationException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public ProblemDetail handleDataIntegrity(DataIntegrityViolationException exception) {
  ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
    "Id inválida o violación de restricción de base de datos");

  problemDetail.setTitle("Violación de integridad de datos");

  return problemDetail;
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
  String message = exception.getBindingResult().getFieldErrors().stream().findFirst()
    .map(error -> error.getField() + ": " + error.getDefaultMessage()).orElse("Validation error");

  ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);

  problemDetail.setTitle("Error de validación");

  return problemDetail;
 }
}
