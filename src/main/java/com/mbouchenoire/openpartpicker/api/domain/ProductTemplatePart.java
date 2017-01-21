package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public final class ProductTemplatePart {

    private final PartType type;
    private final boolean optional;

    public ProductTemplatePart(PartType type, boolean optional) {
        if (type == null)
            throw new IllegalArgumentException("type");

        this.type = type;
        this.optional = optional;
    }

    public PartType getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTemplatePart that = (ProductTemplatePart) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ProductTemplatePart{" +
                "type='" + type + '\'' +
                ", optional=" + optional +
                '}';
    }
}
