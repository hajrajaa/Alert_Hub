package com.mst.actionservice.convertor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mst.actionservice.exception.InvalidConditionFormatException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


import java.util.List;

@Converter(autoApply = true)
public class ConditionConvertor implements AttributeConverter<List<List<Integer>>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<List<Integer>> attribute) {

        try{
            return attribute==null?null:mapper.writeValueAsString(attribute);

        }catch(Exception e){
            throw new InvalidConditionFormatException("Invalid condition format.");
        }

    }

    @Override
    public List<List<Integer>> convertToEntityAttribute(String dbData) {
        try{
            return dbData==null? null:
                    mapper.readValue(dbData, new TypeReference<List<List<Integer>>>() {});


        } catch (Exception e) {
            throw new InvalidConditionFormatException("Condition format in database is invalid.");
        }
    }
}
