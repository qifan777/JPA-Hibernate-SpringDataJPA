package io.qifan.jpa.user;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderTypeConverter implements AttributeConverter<GenderType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GenderType type) {
        return type.getCode();
    }

    @Override
    public GenderType convertToEntityAttribute(Integer integer) {
        return GenderType.codeOf(integer);
    }
}
