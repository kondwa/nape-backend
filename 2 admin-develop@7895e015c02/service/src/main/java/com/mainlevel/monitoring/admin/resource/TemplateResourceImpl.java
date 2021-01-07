package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyHistoryDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyHistoryListDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveySettingsDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateListDTO;
import com.mainlevel.monitoring.admin.api.exception.SurveyNotFoundException;
import com.mainlevel.monitoring.admin.api.resource.TemplateResource;
import com.mainlevel.monitoring.admin.repository.entity.SurveySettingsEntity;
import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;
import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;
import com.mainlevel.monitoring.admin.service.SurveyTemplateService;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * SurveyEntity template management resource
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class TemplateResourceImpl implements TemplateResource {

    @Autowired
    private SurveyTemplateService surveyService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateListDTO> getSurveyTemplates(@RequestParam(name = "unitId", required = false) String unitId) {

        Set<SurveyTemplateEntity> surveys = surveyService.loadSurveysByFilter(unitId);
        List<SurveyTemplateDTO> surveyDTOs = collectionConversionService.convert(surveys, SurveyTemplateDTO.class);

        SurveyTemplateListDTO dto = SurveyTemplateListDTO.builder().surveys(surveyDTOs).build();

        int count = dto.getSurveys() != null ? dto.getSurveys().size() : 0;
        log.info("Found {} surveys. Filtered by unitId: {}", count, unitId);

        return new ResponseEntity<>(dto, OK);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateDTO> getSurveyByVersion(@ApiParam(value = "Survey identifier.") @PathVariable String identifier,
        @ApiParam(value = "The version of survey.") @PathVariable Long version) {

        SurveyTemplateEntity survey = surveyService.loadSurveyVersion(identifier, version);
        if (survey == null) {
            throw new SurveyNotFoundException(identifier);
        }

        SurveyTemplateDTO dto = conversionService.convert(survey, SurveyTemplateDTO.class);
        log.info("Found survey with id {} and version {}", identifier, version);

        return ResponseEntity.ok(dto);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateDTO> getSurveyTemplate(@ApiParam(value = "The survey identifier.") @PathVariable String identifier) {
        ResponseEntity<SurveyTemplateDTO> result;
        SurveyTemplateEntity survey = surveyService.loadSurvey(identifier);

        if (survey != null) {
            SurveyTemplateDTO dto = conversionService.convert(survey, SurveyTemplateDTO.class);
            result = new ResponseEntity<>(dto, OK);
            log.info("Found survey with id: {}", dto.getIdentifier());
        } else {
            log.info("Didn't find survey with id: {}", identifier);
            result = new ResponseEntity<>(NOT_FOUND);
        }

        return result;
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateDTO> createSurveyTemplate(
        @ApiParam(value = "The survey to be created") @RequestBody @Valid SurveyTemplateDTO surveyDTO) {

        SurveyTemplateEntity createSurvey = surveyService.saveSurveyTemplate(conversionService.convert(surveyDTO, SurveyTemplateEntity.class));
        SurveyTemplateDTO dto = conversionService.convert(createSurvey, SurveyTemplateDTO.class);

        String username = authenticationAccessService.getCurrentUsername();
        log.info("Created Survey {} by {}.", dto.getSurveyName(), username);

        return new ResponseEntity<>(dto, CREATED);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateDTO> updateSurvey(@ApiParam(value = "Survey identifier.") @PathVariable String identifier,
        @ApiParam(value = "Update data") @RequestBody SurveyTemplateDTO surveyDTO) {
        SurveyTemplateEntity inSurvye = conversionService.convert(surveyDTO, SurveyTemplateEntity.class);
        inSurvye.setId(identifier);
        SurveyTemplateEntity updatedSurvey = surveyService.updateSurveyTemplate(inSurvye);
        SurveyTemplateDTO dto = conversionService.convert(updatedSurvey, SurveyTemplateDTO.class);

        log.info("Survey id:{} name:{} has been updated by {}.", dto.getIdentifier(), dto.getSurveyName(),
            authenticationAccessService.getCurrentUsername());
        return new ResponseEntity<>(dto, OK);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyTemplateDTO> updateSurveySettings(@ApiParam(value = "Survey identifier.") @PathVariable String identifier,
        @ApiParam(value = "Settings data") @RequestBody SurveySettingsDTO settingsDTO) {

        SurveyTemplateEntity updatedSurvey =
            surveyService.updateSurveySettings(identifier, conversionService.convert(settingsDTO, SurveySettingsEntity.class));
        SurveyTemplateDTO dto = conversionService.convert(updatedSurvey, SurveyTemplateDTO.class);

        String username = authenticationAccessService.getCurrentUsername();
        log.info("Survey settings for id:{} name:{} have been updated by {}.", dto.getIdentifier(), dto.getSurveyName(), username);

        return new ResponseEntity<>(dto, OK);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<Void> deleteSurvey(@ApiParam(value = "Survey identifier.") @PathVariable String identifier) {
        surveyService.deactivateSurvey(identifier);

        log.info("Survey {} has been deactivated by {}", identifier, authenticationAccessService.getCurrentUsername());

        return ResponseEntity.noContent().build();
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<SurveyHistoryListDTO> getHistory(@ApiParam(value = "Survey identifier.") @PathVariable String identifier) {
        List<TemplateEntity> history = surveyService.getHistory(identifier);

        List<SurveyHistoryDTO> historyDTOs = collectionConversionService.convert(history, SurveyHistoryDTO.class);
        SurveyHistoryListDTO historyListDTO = SurveyHistoryListDTO.builder().historyList(historyDTOs).build();

        log.info("Found history for survey {} with {} elements.", identifier,
            historyListDTO.getHistoryList() != null ? historyListDTO.getHistoryList().size() : 0);

        return ResponseEntity.ok(historyListDTO);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<Void> deleteSurveyVersion(@ApiParam(value = "The identifier of survey.") @PathVariable String identifier,
        @ApiParam(value = "The version of survey.") @PathVariable Long version) {

        surveyService.deactivateSurveyVersion(identifier, version);

        String username = authenticationAccessService.getCurrentUsername();
        log.info("Survey {} with version {} has been deactivated by {}", identifier, version, username);

        return ResponseEntity.noContent().build();
    }

}
