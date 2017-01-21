package com.mbouchenoire.openpartpicker.api.service;

import com.mbouchenoire.openpartpicker.api.domain.*;
import com.mbouchenoire.openpartpicker.api.repository.PartRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mbouchenoire
 */
public class SuggestionService {

    private final PartRepository partRepository;

    public SuggestionService(PartRepository partRepository) {
        if (partRepository == null)
            throw new IllegalArgumentException("partRepository");

        this.partRepository = partRepository;
    }

    public List<List<Part>> suggest(ProductTemplate productTemplate) {
        final List<List<Part>> suggestions = new ArrayList<>();

        for(ProductTemplatePart templatePart: productTemplate.getParts()) {
            final Set<Part> keyTemplateParts = this.partRepository.findParts(templatePart.getType());

            for(Part keyTemplatePart: keyTemplateParts) {
                final List<Part> selectedParts = new ArrayList<>();
                selectPart(keyTemplatePart, this.partRepository, selectedParts);
                suggestions.add(selectedParts);
            }
        }

        return suggestions;
    }

    private static void selectPart(Part part, PartRepository partRepository, List<Part> selectedParts) {
        selectedParts.add(part);

        for(PartDependency constraint: part.getConstraints()) {
            final List<Part> selectedConstrainedParts = new ArrayList<>(filter(selectedParts, constraint));

            if (selectedConstrainedParts.size() < constraint.getQuantity()) {
                for(int i = 0; i < (constraint.getQuantity() - selectedConstrainedParts.size()); i++) {
                    final Set<Part> candidateParts = partRepository.findParts(constraint.getPartType());
                    final List<Part> eligibleConstrainedParts = new ArrayList<>(filter(candidateParts, constraint));

                    eligibleConstrainedParts.stream().findFirst().ifPresent(p -> {
                        selectPart(p, partRepository, selectedParts);
                    });
                }
            }
        }
    }

    private static Collection<Part> filter(Collection<Part> parts, PartType type) {
        return parts.stream()
                    .filter(p -> p.getType().equals(type))
                    .collect(Collectors.toList());
    }

    private static Collection<Part> filter(Collection<Part> parts, PartDependency constraint) {
        parts = filter(parts, constraint.getPartType());

        final Collection<Part> filteredParts = new ArrayList<>(parts.size());

        for(Part part: parts) {
            boolean partOk = true;

            for(PartAttributeDependency partAttributeDependency: constraint.getAttributes()) {
                final String attributeValue = part.getAttribute(partAttributeDependency.getAttribute()).getValue();
                final String dependencyAttributeValue = partAttributeDependency.getValue();

                switch (partAttributeDependency.getType()) {
                    case EQUALS:
                        partOk |= !attributeEquals(attributeValue, dependencyAttributeValue);
                        break;
                    case GREATER_THAN:
                        partOk |= !attributeGreaterThan(attributeValue, dependencyAttributeValue);
                        break;
                    case LESS_THAN:
                        partOk |= !attributeLessThan(attributeValue, dependencyAttributeValue);
                        break;
                    case IN:
                        partOk |= !attributeIn(attributeValue, dependencyAttributeValue);
                        default:
                            throw new IllegalStateException("This should never happen.");
                }
            }

            if (partOk) {
                filteredParts.add(part);
            }
        }

        return filteredParts;
    }

    private static boolean attributeEquals(String attributeValue, String targetValue) {
        return attributeValue.equals(targetValue);
    }

    private static boolean attributeGreaterThan(String attributeValue, String targetValue) {
        return new BigDecimal(attributeValue).compareTo(new BigDecimal(targetValue)) >= 0;
    }

    public static boolean attributeLessThan(String attributeValue, String targetValue) {
        return new BigDecimal(attributeValue).compareTo(new BigDecimal(targetValue)) < 0;
    }

    public static boolean attributeIn(String attributeValue, String targetValue) {
        throw new UnsupportedOperationException("attributeIn");
    }
}
