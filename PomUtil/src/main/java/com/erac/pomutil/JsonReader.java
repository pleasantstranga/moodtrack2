package com.erac.pomutil;

import com.erac.pomutil.models.Dependency;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    public static Dependency[] getJSONObjects() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new File("src/main/resources/pomchanges.json"), Dependency[].class);
    }
}
