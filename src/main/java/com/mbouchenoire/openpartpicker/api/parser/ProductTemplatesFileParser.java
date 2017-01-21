package com.mbouchenoire.openpartpicker.api.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbouchenoire.openpartpicker.api.domain.PartType;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplate;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplatePart;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplateType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author mbouchenoire
 */
public final class ProductTemplatesFileParser {

    public Set<ProductTemplate> parse(String filePath) {
        final Resource resource = new ClassPathResource(filePath);

        try {
            final InputStream in = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Map result = mapper.readValue(in, Map.class);

            return parse(result);
        } catch (IOException ioe) {
            throw new IllegalStateException("cannot parse part types JSON file", ioe);
        }
    }

    private static Set<ProductTemplate> parse(Map<Object, Object> result) {
        final List<Map<String, Object>> productTemplatesList = (List<Map<String, Object>>)result.get("product_templates");
        final Set<ProductTemplate> productTemplates = new HashSet<>(productTemplatesList.size());

        for(Map<String, Object> productTemplateAttributesMap: productTemplatesList) {
            final String productTemplateTypeCode = (String)productTemplateAttributesMap.get("type");
            final ProductTemplateType productTemplateType = ProductTemplateType.fromCode(productTemplateTypeCode);

            final List<Map<String, Object>> partsList = (List<Map<String, Object>>)productTemplateAttributesMap.get("parts");
            final Set<ProductTemplatePart> parts = new HashSet<>(partsList.size());

            for(Map<String, Object> partAttributesMap: partsList) {
                final String partTypeCode = (String)partAttributesMap.get("type");
                final PartType partType = PartType.fromCode(partTypeCode);
                final boolean optional = (boolean)partAttributesMap.get("optional");

                final ProductTemplatePart part = new ProductTemplatePart(partType, optional);
                parts.add(part);
            }

            final ProductTemplate productTemplate = new ProductTemplate(productTemplateType, parts);
            productTemplates.add(productTemplate);
        }

        return productTemplates;
    }
}
