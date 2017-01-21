package com.mbouchenoire.openpartpicker.api.repository;

import com.mbouchenoire.openpartpicker.api.domain.ProductTemplate;
import com.mbouchenoire.openpartpicker.api.domain.ProductTemplateType;
import com.mbouchenoire.openpartpicker.api.parser.ProductTemplatesFileParser;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class ProductTemplateJsonFileRepository implements ProductTemplateRepository {

    private final String filePath;
    private final ProductTemplatesFileParser productTemplatesFileParser;

    public ProductTemplateJsonFileRepository(String filePath, ProductTemplatesFileParser productTemplatesFileParser) {
        super();

        if (filePath == null)
            throw new IllegalArgumentException("filePath");

        if (productTemplatesFileParser == null)
            throw new IllegalArgumentException("productTemplatesFileParser");

        this.filePath = filePath;
        this.productTemplatesFileParser = productTemplatesFileParser;
    }

    public Optional<ProductTemplate> findOne(ProductTemplateType type) {
        return this.findAll().stream()
                             .filter(p -> p.getType().equals(type))
                             .findFirst();
    }

    @Override
    public Set<ProductTemplate> findAll() {
        return productTemplatesFileParser.parse(this.filePath);
    }
}
