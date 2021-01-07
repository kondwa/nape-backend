package com.mainlevel.monitoring.admin.service.impl;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.api.exception.SaveByNullException;
import com.mainlevel.monitoring.admin.repository.PredefinedListItemRepository;
import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;
import com.mainlevel.monitoring.admin.service.PredefinedListService;

import lombok.extern.slf4j.Slf4j;

/**
 * Managing with all predefined lists.
 */
@Slf4j
@Service
public class PredefinedListServiceImpl implements PredefinedListService {

    @Autowired
    private PredefinedListItemRepository predefinedRepository;

    @Override
    public Set<String> getAvailableLists() {

        Set<String> names = new HashSet<>();

        Set<PredefinedListItemEntity> types = predefinedRepository.findAllTypes();
        types.forEach(type -> {
            names.add(type.getType());
        });
        return names;
    }

    @Override
    public List<PredefinedListItemEntity> getItems(final String listName) {
        List<PredefinedListItemEntity> items = this.predefinedRepository.findByType(listName.toUpperCase());
        return items;
    }

    @Override
    public PredefinedListItemEntity saveByCode(PredefinedListItemEntity item) {

        if (item.getCode() == null) {
            throw new SaveByNullException(PredefinedListItemEntity.class, "code");
        }

        if (item.getType() == null) {
            throw new SaveByNullException(PredefinedListItemEntity.class, "type");
        }

        PredefinedListItemEntity dbItem = predefinedRepository.findByCodeAndType(item.getCode(), item.getType());
        if (dbItem == null) {
            String id = isEmpty(item.getId()) ? UUID.randomUUID().toString() : item.getId();
            dbItem = PredefinedListItemEntity.builder().id(id).code(item.getCode()).type(item.getType()).build();
        }

        dbItem.setCategory(item.getCategory());

        PredefinedListItemEntity newItem = predefinedRepository.save(dbItem);

        log.debug("Saved predefined list ítem {} of type {} by the following code: {}", item.getId(), item.getType(), item.getCode());

        return newItem;
    }

}
