package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.admin.api.dto.language.LanguageListDTO;
import com.mainlevel.monitoring.admin.api.resource.LanguageResource;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.admin.service.LanguageService;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link LanguageResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class LanguageResourceImpl implements LanguageResource {

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<LanguageListDTO> findAll() {

        final List<LanguageEntity> languageEntites = languageService.findAll();

        List<LanguageDTO> languageDTOs = collectionConversionService.convert(languageEntites, LanguageDTO.class);

        log.debug("{} languages found in database.", languageEntites.size());

        LanguageListDTO languageDTO = LanguageListDTO.builder().languages(languageDTOs).build();

        return ResponseEntity.ok(languageDTO);
    }
}
