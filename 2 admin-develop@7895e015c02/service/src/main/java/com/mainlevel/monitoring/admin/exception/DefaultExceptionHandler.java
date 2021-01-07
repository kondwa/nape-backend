package com.mainlevel.monitoring.admin.exception;

import static com.mainlevel.monitoring.common.constant.ErrorCodes.INDICATOR_NOT_FOUND;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.SURVEY_NOT_FOUND;
import static com.mainlevel.monitoring.common.constant.ErrorCodes.UNIT_NOT_FOUND;
import static com.mainlevel.monitoring.common.resource.dto.SeverityType.WARNING;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mainlevel.monitoring.admin.api.exception.IndicatorNotFoundException;
import com.mainlevel.monitoring.admin.api.exception.SurveyNotFoundException;
import com.mainlevel.monitoring.admin.api.exception.UnitNotFoundException;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

/**
 * Default exception handler for all raised exceptions in survey-admin microservice.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    private ContentTypeJSONHeader header;

    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * Handles a raised UnitNotFoundException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = UnitNotFoundException.class)
    public ResponseEntity<ErrorDTO> unitNotFoundException(UnitNotFoundException exception) throws Exception {
        logger.info("UnitNotFoundException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(UNIT_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised IndicatorNotFoundException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(value = IndicatorNotFoundException.class)
    public ResponseEntity<ErrorDTO> indicatorNotFoundException(IndicatorNotFoundException exception) throws Exception {
        logger.info("IndicatorNotFoundException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(INDICATOR_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

    /**
     * Handles a raised SurveyNotFoundException.
     *
     * @param exception the exception
     * @return the error DTO
     * @throws Exception when an error occurs
     */
    @ExceptionHandler(SurveyNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleSurveyIllegalModificationException(Exception exception) throws Exception {

        logger.info("SurveyNotFoundException message: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder().code(SURVEY_NOT_FOUND).message(exception.getMessage()).severity(WARNING).build();
        return ResponseEntity.status(NOT_FOUND).headers(header).body(errorDTO);
    }

}
