package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public final class PartAttributeDependency {

    private final String attribute;
    private final PartAttributeDependencyType type;
    private final String value;

    public PartAttributeDependency(String attribute, PartAttributeDependencyType type, String value) {
        if (attribute == null)
            throw new IllegalArgumentException("attribute");

        if (type == null)
            throw new IllegalArgumentException("type");

        if (value == null)
            throw new IllegalArgumentException("value");

        this.attribute = attribute;
        this.type = type;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public PartAttributeDependencyType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartAttributeDependency that = (PartAttributeDependency) o;

        if (!attribute.equals(that.attribute)) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = attribute.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
