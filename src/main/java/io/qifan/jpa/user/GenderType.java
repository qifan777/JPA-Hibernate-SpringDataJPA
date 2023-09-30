package io.qifan.jpa.user;


import java.util.Arrays;
import lombok.Getter;

@Getter
public enum GenderType {
  MALE(0, "男"),
  FEMALE(1, "女"),
  PRIVATE(2, "保密");

  private final Integer code;
  private final String name;

  GenderType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }


  public static GenderType codeOf(Integer code) {
    return Arrays.stream(GenderType.values())
        .filter(type -> type.getCode()
            .equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("枚举不存在"));
  }

}
