package com.behappy.api;

import com.behappy.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import javax.naming.AuthenticationException;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    // Generic exception that we did not think about
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MessagingException.class})
    public ResponseEntity<String> handleMailNotSent(MessagingException ex, WebRequest request) {
        return new ResponseEntity<>("Could not send mail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {TherapyAlreadyExistsException.class})
    public ResponseEntity<String> handleTherapyAlreadyExists(TherapyAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NoSuchTherapyException.class, NoSuchUserException.class, NoSuchInvitationException.class})
    public ResponseEntity<String > handleNonExistentData(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RoleNotAvailableException.class})
    public ResponseEntity<String> handleRoleNotAvailable(RoleNotAvailableException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {EmailTakenException.class})
    public ResponseEntity<String> handleRoleNotAvailable(EmailTakenException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {UserAlreadyInTherapyException.class})
    public ResponseEntity<String> handleUserAlreadyInTherapy(UserAlreadyInTherapyException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {SecurityException.class, AuthenticationException.class})
    public ResponseEntity<String> handleSecurityException(SecurityException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ActionNotAllowedException.class})
    public ResponseEntity<String> handleActionNotAllowed(ActionNotAllowedException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
