package io.qifan.jpa.menu;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MenuTypeConverter implements AttributeConverter<MenuType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(MenuType type) {
    return type.getCode();
  }

  @Override
  public MenuType convertToEntityAttribute(Integer integer) {
    return MenuType.codeOf(integer);
  }
}
