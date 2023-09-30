package io.qifan.jpa.menu;


import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MenuType {
  DIRECTORY(1, "目录"),
  MENU(2, "菜单"),
  BUTTON(3, "按钮");

  private final Integer code;
  private final String name;

  MenuType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }


  public static MenuType codeOf(Integer code) {
    return Arrays.stream(MenuType.values())
        .filter(type -> type.getCode()
            .equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("枚举不存在"));
  }

}
