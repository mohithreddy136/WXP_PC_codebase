package com.utils.activecare;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JSONUtil {

    private JSONUtil() {}
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
