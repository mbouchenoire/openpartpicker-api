package com.mbouchenoire.openpartpicker.api.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbouchenoire.openpartpicker.api.domain.PartType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mbouchenoire
 */
public final class PartTypesFileParser {

    public Map<PartType, String> parse(String filePath) {
        final Resource resource = new ClassPathResource(filePath);

        try {
            final InputStream in = resource.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Map result = mapper.readValue(in, Map.class);

            return parse(result);
        } catch (IOException ioe) {
            throw new IllegalStateException("cannot parse part types JSON file", ioe);
        }
    }

    private static Map<PartType, String> parse(Map<Object, Object> result) {
        final List<Object> partTypes = (List<Object>)result.get("part_types");
        final Map<PartType, String> files = new HashMap<>(partTypes.size());

        for(Object config: partTypes) {
            final Map<String, String> partTypeConfig = (Map<String, String>)config;

            final String partTypeCode = partTypeConfig.get("type");
            final String partTypeFile = partTypeConfig.get("file");

            final PartType partType = PartType.fromCode(partTypeCode);

            files.put(partType, partTypeFile);
        }

        return files;
    }
}
