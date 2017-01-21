package com.mbouchenoire.openpartpicker.api.config;

import com.mbouchenoire.openpartpicker.api.parser.PartJsonFileParser;
import com.mbouchenoire.openpartpicker.api.parser.PartTypesFileParser;
import com.mbouchenoire.openpartpicker.api.parser.ProductTemplatesFileParser;
import com.mbouchenoire.openpartpicker.api.repository.PartJsonFileRepository;
import com.mbouchenoire.openpartpicker.api.repository.PartRepository;
import com.mbouchenoire.openpartpicker.api.repository.ProductTemplateJsonFileRepository;
import com.mbouchenoire.openpartpicker.api.repository.ProductTemplateRepository;
import com.mbouchenoire.openpartpicker.api.service.SuggestionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mbouchenoire
 */
@Configuration
public class Beans {

    private static final String PART_TYPES_FILE_PATH = "part_types.json";
    private static final String PRODUCT_TEMPLATES_FILE_PATH = "product_templates.json";

    @Bean
    public PartRepository getPartRepository() {
        return new PartJsonFileRepository(PART_TYPES_FILE_PATH, new PartTypesFileParser(), new PartJsonFileParser());
    }

    @Bean
    public ProductTemplateRepository getProductTemplateRepository() {
        return new ProductTemplateJsonFileRepository(PRODUCT_TEMPLATES_FILE_PATH, new ProductTemplatesFileParser());
    }

    @Bean
    public SuggestionService getSuggestionService(PartRepository partRepository) {
        return new SuggestionService(partRepository);
    }
}
