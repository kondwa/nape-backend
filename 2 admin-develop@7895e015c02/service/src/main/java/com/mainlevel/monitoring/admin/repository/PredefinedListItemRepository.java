package com.mainlevel.monitoring.admin.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;

/**
 * MongoDB repository for {@link PredefinedListItemEntity} entity.
 */
public interface PredefinedListItemRepository extends MongoRepository<PredefinedListItemEntity, String> {

    /**
     * Find all available predefined list types.
     *
     * @return the distinct list of types
     */
    @Query(value = "{ }", fields = "{ 'type': 1  }")
    Set<PredefinedListItemEntity> findAllTypes();

    /**
     * Find the predefined list items for the given type.
     *
     * @param type the list type
     * @return the list of items
     */
    List<PredefinedListItemEntity> findByType(String type);

    /**
     * Find the predefined list item for the given code and type.
     *
     * @param code the country code
     * @param type the list type
     * @return the entity
     */
    PredefinedListItemEntity findByCodeAndType(String code, String type);

}
