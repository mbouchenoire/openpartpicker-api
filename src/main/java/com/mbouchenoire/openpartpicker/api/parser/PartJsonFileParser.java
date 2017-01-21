package com.mbouchenoire.openpartpicker.api.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbouchenoire.openpartpicker.api.domain.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author mbouchenoire
 */
public final class PartJsonFileParser {

    public Set<Part> parse(String filePath, PartType partType) {
        final Resource resource = new ClassPathResource(filePath);

        try {
            final InputStream in = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Map result = mapper.readValue(in, Map.class);

            return parse(result, partType);
        } catch (IOException ioe) {
            throw new IllegalStateException("cannot parse part JSON file", ioe);
        }
    }

    private static Set<Part> parse(Map<String, List<Map<String, Object>>> result, PartType partType) {
        final String rootKey = result.keySet().stream().findFirst().get();
        final List<Map<String, Object>> partsList = result.get(rootKey);
        final Set<Part> parts = new HashSet<>(partsList.size());

        for(Map<String, Object> partAttributesMap: partsList) {
            final String name = (String)partAttributesMap.get("name");

            final Map<String, String> attributesMap = (Map<String, String>)partAttributesMap.get("attributes");
            final Set<PartAttribute> attributes = parseAttributes(attributesMap);

            final List<Map<String, Object>> dependenciesList = (List<Map<String, Object>>)partAttributesMap.get("dependencies");
            final Set<PartDependency> dependencies = parseDependencies(dependenciesList);

            final List<Map<String, String>> linksList = (List<Map<String, String>>)partAttributesMap.get("links");
            final Set<PartLink> partLinks = parseLinks(linksList);

            final Part part = new Part(name, partType, attributes, dependencies, partLinks);
            parts.add(part);
        }

        return parts;
    }

    private static Set<PartAttribute> parseAttributes(Map<String, String> attributesMap) {
        final Set<PartAttribute> attributes = new HashSet<>(attributesMap.size());

        for(String key: attributesMap.keySet()) {
            final String value = attributesMap.get(key);
            final PartAttribute attribute = new PartAttribute(key, value);
            attributes.add(attribute);
        }

        return attributes;
    }

    private static Set<PartDependency> parseDependencies(List<Map<String, Object>> dependenciesList) {
        if (dependenciesList == null)
            return Collections.emptySet();

        final Set<PartDependency> dependencies = new HashSet<>(dependenciesList.size());

        for(Map<String, Object> dependencyAttributesMap: dependenciesList) {
            final String partTypeCode = (String)dependencyAttributesMap.get("type");
            final PartType partType = PartType.fromCode(partTypeCode);

            final Object boolOptional = dependencyAttributesMap.get("optional");
            final boolean optional = boolOptional == null ? false : (boolean)boolOptional;
            final int quantity = (int)dependencyAttributesMap.get("quantity");

            final List<Map<String, String>> attributesList = (List<Map<String, String>>)dependencyAttributesMap.get("dependencies");
            final Set<PartAttributeDependency> attributes = parsePartAttributesDependencies(attributesList);

            final PartDependency dependency = new PartDependency(partType, optional, quantity, attributes);
            dependencies.add(dependency);
        }

        return dependencies;
    }

    private static Set<PartAttributeDependency> parsePartAttributesDependencies(List<Map<String, String>> partAttributesDependenciesMap) {
        if (partAttributesDependenciesMap == null)
            return Collections.emptySet();

        final Set<PartAttributeDependency> partAttributeDependencies = new HashSet<>(partAttributesDependenciesMap.size());

        for(Map<String, String> partAttributeDependencyAttributesMap: partAttributesDependenciesMap) {
            final String partAttributeKey = partAttributeDependencyAttributesMap.get("type");

            PartAttributeDependencyType partAttributeDependencyType = null;
            String partAttributeDependencyValue = null;

            for(String partAttributeDependencyKey: partAttributeDependencyAttributesMap.keySet()) {
                final Optional<PartAttributeDependencyType> optionalPartAttributeDependencyType
                    =  parsePartAttributeDependencyType(partAttributeDependencyKey);

                if (optionalPartAttributeDependencyType.isPresent()) {
                    partAttributeDependencyType = optionalPartAttributeDependencyType.get();
                    partAttributeDependencyValue = partAttributeDependencyAttributesMap.get(partAttributeDependencyKey);
                }
            }

            final PartAttributeDependency dependency =
                    new PartAttributeDependency(partAttributeKey, partAttributeDependencyType, partAttributeDependencyValue);

            partAttributeDependencies.add(dependency);
        }

        return partAttributeDependencies;
    }

    private static final Optional<PartAttributeDependencyType> parsePartAttributeDependencyType(String partAttributeDependencyTypeCode) {
            if (partAttributeDependencyTypeCode.equals("eq")) {
                return Optional.of(PartAttributeDependencyType.EQUALS);
            } else if (partAttributeDependencyTypeCode.equals("gt")) {
                return Optional.of(PartAttributeDependencyType.GREATER_THAN);
            } else if (partAttributeDependencyTypeCode.equals("lt")) {
                return Optional.of(PartAttributeDependencyType.LESS_THAN);
            } else if (partAttributeDependencyTypeCode.equals("in")) {
                return Optional.of(PartAttributeDependencyType.IN);
            } else {
                return Optional.empty();
            }
    }

    private static Set<PartLink> parseLinks(List<Map<String, String>> linksList) {
        if (linksList == null)
            return Collections.emptySet();

        final Set<PartLink> links = new HashSet<>(linksList.size());

        for(Map<String, String> linkAttributesMap: linksList) {
            final String linkTitle = linkAttributesMap.get("title");
            final String linkHref = linkAttributesMap.get("href");
            final PartLink link = new PartLink(linkTitle, linkHref);
            links.add(link);
        }

        return links;
    }
}
