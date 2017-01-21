package com.mbouchenoire.openpartpicker.api.repository;

import com.mbouchenoire.openpartpicker.api.domain.Part;
import com.mbouchenoire.openpartpicker.api.domain.PartType;
import com.mbouchenoire.openpartpicker.api.parser.PartJsonFileParser;
import com.mbouchenoire.openpartpicker.api.parser.PartTypesFileParser;

import java.util.Map;
import java.util.Set;

/**
 * @author mbouchenoire
 */
public final class PartJsonFileRepository implements PartRepository {


    private final String filePath;
    private final PartTypesFileParser partTypesFileParser;
    private final PartJsonFileParser partJsonFileParser;

    public PartJsonFileRepository(String filePath, PartTypesFileParser partTypesFileParser, PartJsonFileParser partJsonFileParser) {
        super();

        if (filePath == null)
            throw new IllegalArgumentException("filePath");

        if (partTypesFileParser == null)
            throw new IllegalArgumentException("partTypesFileParser");

        if (partJsonFileParser == null)
            throw new IllegalArgumentException("partJsonFileParser");

        this.filePath = filePath;
        this.partTypesFileParser = partTypesFileParser;
        this.partJsonFileParser = partJsonFileParser;
    }

    public Set<Part> findParts(PartType type) {
        if (type == null)
            throw new IllegalArgumentException("type");

        final Map<PartType, String> partTypesPaths = this.partTypesFileParser.parse(this.filePath);
        final String partTypePath = partTypesPaths.get(type);

        if (partTypePath == null)
            throw new IllegalStateException(type + " does not have a JSON file");

        final Set<Part> parts = this.partJsonFileParser.parse(partTypePath, type);

        return parts;
    }
}
