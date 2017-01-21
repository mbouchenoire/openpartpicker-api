package com.mbouchenoire.openpartpicker.api.domain;

import java.util.Collections;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class PartDependency {

    private final PartType partType;
    private final boolean optional;
    private final int quantity;
    private final Set<PartAttributeDependency> attributes;

    public PartDependency(PartType partType, boolean optional, int quantity, Set<PartAttributeDependency> attributes) {
        if (partType == null)
            throw new IllegalArgumentException("partType");

        if (quantity < 0)
            throw new IllegalArgumentException("quantity");

        this.partType = partType;
        this.optional = optional;
        this.quantity = quantity;
        this.attributes = attributes == null ? Collections.emptySet() : Collections.unmodifiableSet(attributes);
    }

    public PartType getPartType() {
        return partType;
    }

    public boolean isOptional() {
        return optional;
    }

    public int getQuantity() {
        return quantity;
    }

    public Set<PartAttributeDependency> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartDependency that = (PartDependency) o;

        if (optional != that.optional) return false;
        return partType.equals(that.partType);
    }

    @Override
    public int hashCode() {
        int result = partType.hashCode();
        result = 31 * result + (optional ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PartDependency{" +
                "partType='" + partType + '\'' +
                ", optional=" + optional +
                ", quantity=" + quantity +
                ", attributes=" + attributes +
                '}';
    }
}
