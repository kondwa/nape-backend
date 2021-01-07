package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.predefined.PredefinedListDTO;
import com.mainlevel.monitoring.admin.api.dto.predefined.PredefinedListItemDTO;
import com.mainlevel.monitoring.admin.api.dto.predefined.PredefinedListItemListDTO;
import com.mainlevel.monitoring.admin.api.resource.PredefinedListResource;
import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;
import com.mainlevel.monitoring.admin.service.PredefinedListService;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link PredefinedListResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class PredefinedListResourceImpl implements PredefinedListResource {

    @Autowired
    private PredefinedListService predefinedListService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<PredefinedListDTO> getAvailabeLists() {

        final Set<String> availableLists = predefinedListService.getAvailableLists();

        log.debug("We found {} possible Lists.", availableLists.size());

        PredefinedListDTO result = PredefinedListDTO.builder().items(availableLists).build();

        return ResponseEntity.ok(result);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<PredefinedListItemListDTO> getListsItems(@PathVariable final String listName) {

        final List<PredefinedListItemEntity> listItems = predefinedListService.getItems(listName);

        log.debug("We found {} items for list {}.", listItems.size(), listName);

        List<PredefinedListItemDTO> items = collectionConversionService.convert(listItems, PredefinedListItemDTO.class);

        PredefinedListItemListDTO result = PredefinedListItemListDTO.builder().items(items).build();

        return ResponseEntity.ok(result);
    }

}
