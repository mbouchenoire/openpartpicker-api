package com.mbouchenoire.openpartpicker.api.domain;

import java.util.Collections;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class ProductTemplate {

    private final ProductTemplateType type;
    private final Set<ProductTemplatePart> parts;

    public ProductTemplate(ProductTemplateType type, Set<ProductTemplatePart> parts) {
        if (type == null)
            throw new IllegalArgumentException("type");

        this.type = type;
        this.parts = parts == null ? Collections.emptySet() : Collections.unmodifiableSet(parts);
    }

    public ProductTemplateType getType() {
        return type;
    }

    public Set<ProductTemplatePart> getParts() {
        return parts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTemplate productTemplate = (ProductTemplate) o;

        return type.equals(productTemplate.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ProductTemplate{" +
                "type='" + type + '\'' +
                ", parts=" + parts +
                '}';
    }
}
