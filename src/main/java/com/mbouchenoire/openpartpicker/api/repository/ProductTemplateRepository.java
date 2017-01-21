package com.mbouchenoire.openpartpicker.api.repository;

import com.mbouchenoire.openpartpicker.api.domain.ProductTemplate;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplateType;

import java.util.Optional;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public interface ProductTemplateRepository {

    Optional<ProductTemplate> findOne(ProductTemplateType type);
    Set<ProductTemplate> findAll();
}
