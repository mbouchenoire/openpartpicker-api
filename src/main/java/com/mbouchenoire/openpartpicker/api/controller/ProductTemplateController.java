package com.mbouchenoire.openpartpicker.api.controller;

import com.mbouchenoire.openpartpicker.api.domain.ProductTemplate;
import com.mbouchenoire.openpartpicker.api.repository.ProductTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author mbouchenoire
 */
@RestController
public final class ProductTemplateController {

    private final ProductTemplateRepository productTemplateRepository;

    @Autowired
    public ProductTemplateController(ProductTemplateRepository productTemplateRepository) {
        if (productTemplateRepository == null)
            throw new IllegalArgumentException("productTemplateRepository");

        this.productTemplateRepository = productTemplateRepository;
    }

    @RequestMapping(value = "/product_templates", method = RequestMethod.GET)
    public Set<ProductTemplate> productTemplates() {
        return this.productTemplateRepository.findAll();
    }
}
