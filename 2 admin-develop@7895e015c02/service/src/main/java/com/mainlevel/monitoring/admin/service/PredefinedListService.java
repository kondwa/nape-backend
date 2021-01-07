package com.mainlevel.monitoring.admin.service;

import java.util.List;
import java.util.Set;

import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;

/**
 * Managing all predefined lists
 */
public interface PredefinedListService {

    /**
     * Return all available lists.
     *
     * @return the names of predefined lists
     */
    Set<String> getAvailableLists();

    /**
     * Finds target list name in db, and convert it to value-item in target language.
     *
     * @param listName target list name
     * @return list of items in target language.
     */
    List<PredefinedListItemEntity> getItems(String listName);

    /**
     * Save the predefined list entry based on its code value.
     *
     * @param item the new item to save
     * @return the saved item
     */
    PredefinedListItemEntity saveByCode(PredefinedListItemEntity item);
}
