package ir.stream.core.exception;

import ir.stream.core.dto.HttpResponse;
import ir.stream.core.dto.HttpResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;


@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Todo: remove printStackTraces

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<HttpResponse<?>> handleNotFoundException(NotFoundException ex) {
        ex.printStackTrace();
        HttpResponseStatus status = new HttpResponseStatus(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(status),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<HttpResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        ex.printStackTrace();
        HttpResponseStatus status = new HttpResponseStatus(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(status),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(HttpException.class)
    public final ResponseEntity<HttpResponse<?>> handleHttpException(HttpException ex) {
        ex.printStackTrace();
        HttpResponseStatus status = new HttpResponseStatus(
                ex.getMessage(),
                ex.getStatus().value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(status),
                ex.getStatus()
        );
    }

    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity<HttpResponse<?>> handleDuplicateException(NotFoundException ex) {
        HttpResponseStatus status = new HttpResponseStatus(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(status),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<HttpResponse<?>> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        HttpResponseStatus status = new HttpResponseStatus(
                "error in data processing.",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(status),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ex.printStackTrace();
        HttpResponseStatus httpResponseStatus = new HttpResponseStatus(
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                status.value()
        );
        return new ResponseEntity<>(
                new HttpResponse<>(httpResponseStatus),
                status
        );
    }


}

