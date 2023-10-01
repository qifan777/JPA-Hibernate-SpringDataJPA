package io.qifan.jpa;

import io.qifan.jpa.address.Address;
import io.qifan.jpa.menu.Menu;
import io.qifan.jpa.menu.MenuType;
import io.qifan.jpa.role.Role;
import io.qifan.jpa.user.GenderType;
import io.qifan.jpa.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(value = false)
class JPAHibernateSpringDataJPAApplicationTests {

  @Autowired
  EntityManager entityManager;


  @Test
  public void saveRole() {
    ArrayList<Role> roles = new ArrayList<>();
    roles.add(new Role().setName("管理员"));
    roles.add(new Role().setName("测试角色"));
    roles.add(new Role().setName("开发者"));
    roles.add(new Role().setName("普通用户"));
    for (Role role : roles) {
      entityManager.persist(role);
    }
  }

  @Test
  public void saveMenu() {
    ArrayList<Menu> menus = new ArrayList<>();
    menus.add(
        new Menu().setMenuType(MenuType.MENU).setPath("/role").setOrderNum(1).setName("角色管理"));
    menus.add(
        new Menu().setMenuType(MenuType.MENU).setPath("/menu").setOrderNum(2).setName("菜单管理"));
    menus.add(
        new Menu().setMenuType(MenuType.MENU).setPath("/user").setOrderNum(0).setName("用户管理"));
    menus.add(
        new Menu().setMenuType(MenuType.DIRECTORY).setPath("/").setOrderNum(0).setName("系统管理"));
    for (Menu menu : menus) {
      entityManager.persist(menu);
    }
  }

  @Test
  public void saveUser() {
    User user = new User().setGender(GenderType.MALE).setNickname("起凡")
        .setAvatar("https://xxxx");
    user.setPhonePassword(
        new UserPhonePassword().setPassword("123456").setPhoneNumber("13656917781").setUser(user));
    user.setWeChat(new UserWeChat().setUser(user).setOpenId("xxxasasdw1d"));
    User user1 = new User().setGender(GenderType.FEMALE).setNickname("测试用户")
        .setAvatar("https://xxxx22");
    user1.setPhonePassword(
        new UserPhonePassword().setPassword("1234567").setPhoneNumber("13651917782")
            .setUser(user1));
    user1.setWeChat(new UserWeChat().setUser(user1).setOpenId("xxdddxasasdd"));
    entityManager.persist(user);
    entityManager.persist(user1);

  }

  @Test
  void contextLoads() {
  }

}
