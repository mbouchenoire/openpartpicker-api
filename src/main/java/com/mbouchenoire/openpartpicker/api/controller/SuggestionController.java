package com.mbouchenoire.openpartpicker.api.controller;

import com.mbouchenoire.openpartpicker.api.domain.Part;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplate;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplateType;
import com.mbouchenoire.openpartpicker.api.repository.PartRepository;
import com.mbouchenoire.openpartpicker.api.repository.ProductTemplateRepository;
import com.mbouchenoire.openpartpicker.api.service.SuggestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * @author mbouchenoire
 */
@RestController
public final class SuggestionController {

    private ProductTemplateRepository productTemplateRepository;
    private SuggestionService suggestionService;

    public SuggestionController(ProductTemplateRepository productTemplateRepository, SuggestionService suggestionService) {
        if (productTemplateRepository == null)
            throw new IllegalArgumentException("productTemplateRepository");

        if (suggestionService == null)
            throw new IllegalArgumentException("suggestionService");

        this.productTemplateRepository = productTemplateRepository;
        this.suggestionService = suggestionService;
    }

    @RequestMapping(value = "/suggest", method = RequestMethod.GET)
    public List<List<Part>> suggest(@RequestParam("product_template") String productTemplateCode) {
        final ProductTemplateType type = ProductTemplateType.fromCode(productTemplateCode);
        final Optional<ProductTemplate> optionalProductTemplate = productTemplateRepository.findOne(type);

        final ProductTemplate productTemplate = optionalProductTemplate
                                                .orElseThrow(() -> new NotFoundException());

        return suggestionService.suggest(productTemplate);
    }
}
