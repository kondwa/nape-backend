package com.mainlevel.monitoring.authentication.exception;

import static com.mainlevel.monitoring.common.constant.ErrorCodes.PASSWORD_GENERATION_FAILED;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.USER_ALREADY_EXISTS;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.USER_NOT_FOUND;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.ERROR;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.INFO;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.WARNING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mainlevel.monitoring.authentication.api.exception.PasswordGenerationFailedException;
import com.mainlevel.monitoring.authentication.api.exception.UserAlreadyExistsException;
import com.mainlevel.monitoring.authentication.api.exception.UserNotFoundException;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles exceptions raised in authentication microservice.
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @Autowired
    private ContentTypeJSONHeader header;

    /**
     * Handles a raised UserAlreadyExistsException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> userAlreadyExistsException(final Exception exception) throws Exception {

        log.info("UserAlreadyExistsException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(USER_ALREADY_EXISTS).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised UserNotFoundException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> userNotFoundException(final UserNotFoundException exception) throws Exception {

        log.info(exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(USER_NOT_FOUND).message(exception.getMessage()).severity(INFO).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised PasswordGenerationFailedException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = PasswordGenerationFailedException.class)
    public ResponseEntity<ErrorDTO> passwordGenerationFailedException(final PasswordGenerationFailedException exception) throws Exception {

        log.error("PasswordGenerationFailedException message: {}, stacktrace {}", exception.getMessage(), exception.getStackTrace());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(PASSWORD_GENERATION_FAILED).message(exception.getMessage()).severity(ERROR).build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).headers(header).body(errorDTO);
    }

}
