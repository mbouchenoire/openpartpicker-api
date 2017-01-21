package com.mbouchenoire.openpartpicker.api.domain;

import java.util.Collections;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class Product {

    private final Set<Part> parts;

    public Product(Set<Part> parts) {
        this.parts = parts == null ? Collections.emptySet() : Collections.unmodifiableSet(parts);
    }

    public Set<Part> getParts() {
        return parts;
    }
}
