package br.com.nexus.goat.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.nexus.goat.exceptions.user.IncorrectPasswordException;
import br.com.nexus.goat.exceptions.user.SamePasswordException;
import br.com.nexus.goat.exceptions.user.UserAlreadyExistsException;
import br.com.nexus.goat.exceptions.user.UserDeletedException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(IncompleteDataException.class)
        public ResponseEntity<StandardError> incompleteDataException(IncompleteDataException e,
                        HttpServletRequest request) {
                String error = "BAD REQUEST";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(IncorrectPasswordException.class)
        public ResponseEntity<StandardError> incorrectPasswordException(IncorrectPasswordException e,
                        HttpServletRequest request) {
                String error = "UNAUTHORIZED";
                HttpStatus status = HttpStatus.UNAUTHORIZED;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(SamePasswordException.class)
        public ResponseEntity<StandardError> samePasswordException(SamePasswordException e,
                        HttpServletRequest request) {
                String error = "BAD REQUEST";
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(UserDeletedException.class)
        public ResponseEntity<StandardError> userDeletedException(UserDeletedException e, HttpServletRequest request) {
                String error = "GONE";
                HttpStatus status = HttpStatus.GONE;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<StandardError> userAlreadyExistsException(UserAlreadyExistsException e,
                        HttpServletRequest request) {
                String error = "CONFLICT";
                HttpStatus status = HttpStatus.CONFLICT;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<StandardError> notFoundException(NotFoundException e,
                        HttpServletRequest request) {
                String error = "NOT FOUND";
                HttpStatus status = HttpStatus.NOT_FOUND;
                StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(err);
        }
}
