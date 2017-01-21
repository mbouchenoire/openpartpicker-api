package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public final class PartAttribute {

    private final String key;
    private final String value;

    public PartAttribute(String key, String value) {
        if (key == null)
            throw new IllegalArgumentException("key");

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartAttribute that = (PartAttribute) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "PartAttribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
