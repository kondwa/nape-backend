package com.mainlevel.monitoring.common.exception.handler;

import static com.mainlevel.monitoring.common.constant.ErrorCodes.ACCESS_DENIED;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.HTTP_MEDIA_TYPE_NOT_SUPPORTED;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.HTTP_MESSAGE_NOT_READABLE;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.METHODARGUMENT_INVALID_TYPE;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.METHODARGUMENT_NOT_VALID;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.NONCE_EXPIRED;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.RESOURCE_NOT_FOUND;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.UNKNOWN_ERROR;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.ERROR;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.WARNING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.mainlevel.monitoring.common.exception.ResourceNotFoundException;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;
import com.mainlevel.monitoring.common.resource.dto.FieldErrorDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Common HTTP exception handler.
 */
@Slf4j
@ControllerAdvice
public class HttpExceptionHandler {

    @Autowired
    private ContentTypeJSONHeader header;

    /**
     * Handles a raised Exception.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> exception(Exception exception) throws Exception {

        log.error("Unexpected error occured. Error message: {}", exception.getMessage(), exception);

        ErrorDTO errorDTO = ErrorDTO.builder().code(UNKNOWN_ERROR).message(exception.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised MethodArgumentNotValidException.
     *
     * @param exception the exception
     * @return the error DTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        log.info("MethodArgumentNotValidException message: {}", exception.getMessage());

        List<FieldErrorDTO> fieldErrorResources = new ArrayList<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldErrorResources.add(FieldErrorDTO.builder().resource(fieldError.getObjectName()).field(fieldError.getField())
                .code(fieldError.getCode()).message(fieldError.getDefaultMessage()).build());
        }

        ErrorDTO errorDTO = ErrorDTO.builder().code(METHODARGUMENT_NOT_VALID).fieldErrors(fieldErrorResources).message(exception.getMessage())
            .severity(ERROR).build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised HttpMessageNotReadableException.
     *
     * @param exception the exception
     * @return the error DTO
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        log.info("HttpMessageNotReadableException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(HTTP_MESSAGE_NOT_READABLE).message(exception.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised HttpMediaTypeNotSupportedException.
     *
     * @param error the exception
     * @return the error DTO
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException error) {

        log.info("HttpMediaTypeNotSupportedException message: {}", error.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(HTTP_MEDIA_TYPE_NOT_SUPPORTED).message(error.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised ResourceNotFoundException.
     *
     * @param error the exception
     * @return the error DTO
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException error) {

        log.info("ResourceNotFoundException message: {}", error.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(RESOURCE_NOT_FOUND).message(error.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised NonceExpiredException.
     *
     * @param exception the exception
     * @return the error DTO
     */
    @ExceptionHandler(NonceExpiredException.class)
    public ResponseEntity<ErrorDTO> handleNonceExpiredException(NonceExpiredException exception) {

        log.info("NonceExpiredException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(NONCE_EXPIRED).message(exception.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(FORBIDDEN).headers(header).body(errorDTO);

    }

    /**
     * Handles a raised AccessDeniedException.
     *
     * @param exception the exception
     * @return the error DTO
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException exception) {

        log.info("AccessDeniedException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(ACCESS_DENIED).message(exception.getMessage()).severity(ERROR).build();

        return ResponseEntity.status(UNAUTHORIZED).headers(header).body(errorDTO);

    }

    /**
     * Handles a raised MethodArgumentTypeMismatchException.
     *
     * @param exception the exception
     * @return the error DTO
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> missingProjectException(MethodArgumentTypeMismatchException exception) {
        String methodName =
            exception.getParameter() != null && exception.getParameter().getMethod() != null ? exception.getParameter().getMethod().getName() : "N/A";
        log.info("MethodArgumentTypeMismatchException message: {}, name: {}, method: {}!", exception.getMessage(), exception.getName(), methodName);

        ErrorDTO errorDTO = ErrorDTO.builder().code(METHODARGUMENT_INVALID_TYPE).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

}
