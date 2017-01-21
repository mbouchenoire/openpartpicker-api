package com.mbouchenoire.openpartpicker.api.controller;

import com.mbouchenoire.openpartpicker.api.domain.Part;
import com.mbouchenoire.openpartpicker.api.domain.PartType;
import com.mbouchenoire.openpartpicker.api.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author mbouchenoire
 */
@RestController
public final class PartController {

    private final PartRepository partRepository;

    @Autowired
    public PartController(PartRepository partRepository) {
        if (partRepository == null)
            throw new IllegalArgumentException("partRepository");

        this.partRepository = partRepository;
    }

    @RequestMapping(value = "/parts", method = RequestMethod.GET)
    public Set<Part> parts(@RequestParam(value="type") String typeCode) {
        final PartType partType = PartType.fromCode(typeCode);
        return this.partRepository.findParts(partType);
    }
}
