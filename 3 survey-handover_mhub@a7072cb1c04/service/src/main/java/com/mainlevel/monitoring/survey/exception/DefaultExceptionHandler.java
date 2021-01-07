package com.mainlevel.monitoring.survey.exception;

import static com.mainlevel.monitoring.common.constant.ErrorCodes.CSV_GENERATION_EXCEPTION;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.ENTITY_LOCK_EXCEPTION;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.PARTICIPANT_SESSION_IS_DUPLICATED;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.PROJECT_NOT_FOUND;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.TARGET_ENTITY_NOT_FOUND;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.UNABLE_TO_EXTEND_LOCK_EXCEPTION;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.USER_ALREADY_EXISTS;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.WRONG_WORKFLOW_STATUS;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.ERROR;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.WARNING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;
import com.mainlevel.monitoring.survey.api.exception.CsvGenerationException;
import com.mainlevel.monitoring.survey.api.exception.DuplicateParticipantSessionException;
import com.mainlevel.monitoring.survey.api.exception.EntityLockException;
import com.mainlevel.monitoring.survey.api.exception.EvaluationQueryNotFoundException;
import com.mainlevel.monitoring.survey.api.exception.LinkExpiredException;
import com.mainlevel.monitoring.survey.api.exception.MissingProjectException;
import com.mainlevel.monitoring.survey.api.exception.ReportingPeriodNotExistException;
import com.mainlevel.monitoring.survey.api.exception.SurveyIllegalModificationException;
import com.mainlevel.monitoring.survey.api.exception.TargetEntityNotFound;
import com.mainlevel.monitoring.survey.api.exception.UnableToExtendLockException;
import com.mainlevel.monitoring.survey.api.exception.WrongWorkflowStatusException;

import lombok.extern.slf4j.Slf4j;

/**
 * Default exception handler for all raised exceptions in survey microservice.
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @Autowired
    private ContentTypeJSONHeader header;

    /**
     * Handles a raised SurveyIllegalModificationException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(SurveyIllegalModificationException.class)
    public ResponseEntity<ErrorDTO> handleSurveyIllegalModificationException(final Exception exception) throws Exception {

        log.info("SurveyIllegalModificationException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(USER_ALREADY_EXISTS).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);

    }

    /**
     * Handles a raised TargetEntityNotFound.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(TargetEntityNotFound.class)
    public ResponseEntity<ErrorDTO> handleTargetEntityNotFound(final Exception exception) throws Exception {

        log.info("TargetEntityNotFound message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(TARGET_ENTITY_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised WrongWorkflowStatusException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(WrongWorkflowStatusException.class)
    public ResponseEntity<ErrorDTO> handleWrongWorkflowStatusException(final Exception exception) throws Exception {

        log.info("WrongWorkflowStatusException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(WRONG_WORKFLOW_STATUS).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised ReportingPeriodNotExistException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(ReportingPeriodNotExistException.class)
    public ResponseEntity<ErrorDTO> handleReportinPeriodNotExistException(final Exception exception) throws Exception {

        log.info("ReportingPeriodNotExistException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(TARGET_ENTITY_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised DuplicateParticipantSessionException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(DuplicateParticipantSessionException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateParticipantSessionException(final Exception exception) throws Exception {

        log.info("DuplicateParticipantSessionException message: {}", exception.getMessage());

        final ErrorDTO errorDTO =
            ErrorDTO.builder().code(PARTICIPANT_SESSION_IS_DUPLICATED).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised MissingProjectException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = MissingProjectException.class)
    public ResponseEntity<ErrorDTO> handleMissingProjectException(final MissingProjectException exception) throws Exception {

        log.info("MissingProjectException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(PROJECT_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised UnableToExtendLockException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = UnableToExtendLockException.class)
    public ResponseEntity<ErrorDTO> handleUnableToExtendLockException(final UnableToExtendLockException exception) throws Exception {

        log.info("UnableToExtendLockException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(UNABLE_TO_EXTEND_LOCK_EXCEPTION).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised UnableToExtendLockException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = LinkExpiredException.class)
    public ResponseEntity<ErrorDTO> handleLinkExpiredException(final LinkExpiredException exception) throws Exception {

        log.info("LinkExpiredException message: {}", exception.getMessage());

        // TODO: Code

        final ErrorDTO errorDTO = ErrorDTO.builder().message(exception.getMessage()).severity(ERROR).build();
        return ResponseEntity.status(GONE).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised EntityLockException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = EntityLockException.class)
    public ResponseEntity<ErrorDTO> handleEntityLockException(final EntityLockException exception) throws Exception {

        log.info("EntityLockException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(ENTITY_LOCK_EXCEPTION).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(BAD_REQUEST).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised EvaluationQueryNotFoundException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = EvaluationQueryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEvaluationQueryNotFoundException(final EvaluationQueryNotFoundException exception) throws Exception {

        log.info("EvaluationQueryNotFoundException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(TARGET_ENTITY_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised CsvGenerationException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = CsvGenerationException.class)
    public ResponseEntity<ErrorDTO> handleCsvGenerationException(final CsvGenerationException exception) throws Exception {

        log.info("CsvGenerationException message: {}", exception.getMessage());

        final ErrorDTO errorDTO = ErrorDTO.builder().code(CSV_GENERATION_EXCEPTION).message(exception.getMessage()).severity(ERROR).build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).headers(header).body(errorDTO);
    }

}
