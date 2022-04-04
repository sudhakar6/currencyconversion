package com.currency.conversion.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    }


    public static String parseNonProtoObj(Object object) {
        if (object == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse object: {}, exception: {}", object.toString(), e);
        }
        return null;
    }

    public static <T> T populateNonProtoObjFromJson(String source, Class<T> tClass) throws IOException {
        return mapper.readValue(source, tClass);
    }


    public static Map<String, Object> readStringAsMap(String jsonString) throws IOException {
        return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });
    }
}