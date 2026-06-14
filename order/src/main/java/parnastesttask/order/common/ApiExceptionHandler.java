package parnastesttask.order.common;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "parnastesttask.order.api")
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.toList());

    log.atWarn()
        .setCause(ex)
        .addKeyValue("validation_errors", errors)
        .addKeyValue("path", request.getRequestURI())
        .log("Validation failed");

    return buildResponse(ex, HttpStatus.BAD_REQUEST, String.join("; ", errors), request);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorResponse> handleNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    log.atWarn()
        .setCause(ex)
        .addKeyValue("path", request.getRequestURI())
        .log("Malformed JSON request");

    return buildResponse(
        ex, HttpStatus.BAD_REQUEST, "Некорректное тело запроса (JSON parsing error)", request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityNotFound(
      EntityNotFoundException ex, HttpServletRequest request) {
    return buildResponse(ex, HttpStatus.NOT_FOUND, "Сущность не найдена", request);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiErrorResponse> handleNoSuchElement(
      NoSuchElementException ex, HttpServletRequest request) {
    return buildResponse(ex, HttpStatus.NOT_FOUND, "Элемент не найден", request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
      DataIntegrityViolationException ex, HttpServletRequest request) {
    log.atWarn()
        .setCause(ex)
        .addKeyValue("path", request.getRequestURI())
        .log("DB constraint violation");

    return buildResponse(ex, HttpStatus.CONFLICT, "Нарушение целостности данных", request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(
      Exception ex, HttpServletRequest request) {
    log.atError()
        .setCause(ex)
        .addKeyValue("exception", ex.getClass().getSimpleName())
        .addKeyValue("message", ex.getMessage())
        .addKeyValue("path", request.getRequestURI())
        .log("Unexpected error");

    return buildResponse(
        ex, HttpStatus.INTERNAL_SERVER_ERROR, "Непредвиденная ошибка. Попробуйте позже", request);
  }

  private ResponseEntity<ApiErrorResponse> buildResponse(
      Exception ex, HttpStatus status, String defaultMessage, HttpServletRequest request) {
    String message = defaultMessage != null ? defaultMessage : ex.getMessage();

    ApiErrorResponse body =
        new ApiErrorResponse(
            message,
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            request.getRequestURI());

    return ResponseEntity.status(status).body(body);
  }
}
