package com.mbouchenoire.openpartpicker.api.repository;

import com.mbouchenoire.openpartpicker.api.domain.Part;
import com.mbouchenoire.openpartpicker.api.domain.PartType;

import java.util.Set;

/**
 * @author mbouchenoire
 */
public interface PartRepository {

    Set<Part> findParts(PartType type);
}
