package com.mbouchenoire.openpartpicker.api.domain;

import java.util.Collections;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class Part {

    private final String name;
    private final PartType type;
    private final Set<PartAttribute> attributes;
    private final Set<PartDependency> constraints;
    private final Set<PartLink> links;

    public Part(String name, PartType type, Set<PartAttribute> attributes, Set<PartDependency> constraints, Set<PartLink> links) {
        if (name == null)
            throw new IllegalArgumentException("name");

        this.name = name;
        this.type = type;
        this.attributes = attributes == null ? Collections.emptySet() : Collections.unmodifiableSet(attributes);
        this.constraints = constraints == null ? Collections.emptySet() :  Collections.unmodifiableSet(constraints);
        this.links = links == null ? Collections.emptySet() :  Collections.unmodifiableSet(links);
    }

    public String getName() {
        return name;
    }

    public PartType getType() {
        return type;
    }

    public PartAttribute getAttribute(String key) {
        return this.getAttributes().stream()
                                   .filter(a -> a.getKey().equals(key))
                                   .findFirst()
                                   .orElseThrow(() -> new IllegalArgumentException(key));
    }

    public Set<PartAttribute> getAttributes() {
        return attributes;
    }

    public Set<PartDependency> getConstraints() {
        return constraints;
    }

    public Set<PartLink> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Part part = (Part) o;

        if (!name.equals(part.name)) return false;
        return type == part.type;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", attributes=" + attributes +
                ", constraints=" + constraints +
                ", links=" + links +
                '}';
    }
}
