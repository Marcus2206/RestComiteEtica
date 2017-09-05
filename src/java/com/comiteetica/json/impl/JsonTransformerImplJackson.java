/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comiteetica.json.impl;

import com.comiteetica.json.JsonTransformer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author cmedina
 */

public class JsonTransformerImplJackson implements JsonTransformer {

    @Override
    public String toJson(Object data) {
        try {//Feature.WRAP_ROOT_VALUE
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Object fromJson(String json, Class clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            // do various things, perhaps:
//            String someJsonString = mapper.writeValueAsString(someClassInstance);
//            SomeClass someClassInstance = mapper.readValue(someJsonString, SomeClass.class)
            
            return objectMapper.readValue(json, clazz);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    //List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
    
}
