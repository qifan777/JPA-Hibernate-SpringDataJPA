package io.qifan.jpa.model;

import io.qifan.jpa.address.Address;
import io.qifan.jpa.menu.Menu;
import io.qifan.jpa.role.Role;
import io.qifan.jpa.role.RoleMenu;
import io.qifan.jpa.user.GenderType;
import io.qifan.jpa.user.User;
import io.qifan.jpa.user.UserPhonePassword;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Slf4j
@Transactional
// 事务在测试环境中默认会回滚
@Rollback(value = false)
public class JPAModelTest {


  @Autowired
  EntityManager entityManager;

  Address address = new Address().setProvince("河南省").setCity("南阳市").setDistrict("方城县")
      .setDetails("友谊路")
      .setHouseNumber("976")
      .setPhoneNumber("+86 13686863075")
      .setRealName("罗富财");
  Address address2 = new Address().setProvince("陕西省").setCity("汉中市").setDistrict("城固县")
      .setDetails("丹景山路")
      .setHouseNumber("29")
      .setPhoneNumber("+86 13686863075")
      .setRealName("罗富财");


  public void deleteAddress() {
    entityManager.createQuery("delete from Address").executeUpdate();
  }

  /**
   * 多对一关联新增
   */
  @Test
  public void manyToOneSave() {
    deleteAddress();
    Address address = new Address().setProvince("河南省").setCity("南阳市").setDistrict("方城县")
        .setDetails("友谊路")
        .setHouseNumber("976")
        .setPhoneNumber("+86 13686863075")
        .setRealName("罗富财");
    Address address2 = new Address().setProvince("陕西省").setCity("汉中市").setDistrict("城固县")
        .setDetails("丹景山路")
        .setHouseNumber("29")
        .setPhoneNumber("+86 13686863075")
        .setRealName("罗富财");
    User user = entityManager.find(User.class, "1");
    address.setUser(user);
    address2.setUser(user);
    entityManager.persist(address);
    entityManager.persist(address2);
  }


  @Test
  public void oneToMany() {
    deleteAddress();
    User user = entityManager.find(User.class, "1");
    address.setUser(user);
    address2.setUser(user);
    // 增加列表
    user.getAddresses().add(address);
    user.getAddresses().add(address2);
    // 保存user到数据库时会级联创建列表内的address。
    entityManager.persist(user);
  }

  @Test
  public void oneToManyRemove() {
    User user = entityManager.find(User.class, "1");
    List<Address> addresses = user.getAddresses();
    log.info("用户地址数量：{}", addresses);
    // 减少列表
    addresses.remove(0);
    // 保存到数据库时，列表内减少的address会被自动删除。
    entityManager.persist(user);
    user = entityManager.find(User.class, "1");
    addresses = user.getAddresses();
    log.info("用户地址数量：{}", addresses);
  }

  @Test
  public void oneToOneUnidirectional() {
    User user = new User().setNickname("起凡");
    user.setGender(GenderType.FEMALE);
    entityManager.persist(user);
    UserPhonePassword userPhonePassword = new UserPhonePassword();
    // 单向关联
    userPhonePassword.setUser(user);
    userPhonePassword.setPhoneNumber("13676417778");
    userPhonePassword.setPassword("123456");
    entityManager.persist(userPhonePassword);
  }

  @Test
  public void oneToOneBidirectional() {
    User user = new User().setNickname("起凡2");
    user.setGender(GenderType.FEMALE);

    UserPhonePassword userPhonePassword = new UserPhonePassword();
    userPhonePassword.setPhoneNumber("13676417718");
    userPhonePassword.setPassword("123456");

    // 双向联系，彼此依赖
    userPhonePassword.setUser(user);
    user.setPhonePassword(userPhonePassword);

    // 由于父实体User中配置了CascadeType.ALL，在创建User时会级联创建PhonePassword。反之则不行
    entityManager.persist(user);
  }

  /**
   * 注意：@ManyToMany比较特殊，关系的拥有方是父实体。 为用户关联所有角色
   */
  @Test
  public void manyToManySave() {
    List<Role> roleList = entityManager.createQuery("select r from Role  r", Role.class)
        .getResultList();
    User user = entityManager.find(User.class, "1");
    user.getRoles().addAll(roleList);
    entityManager.persist(user);
  }

  /**
   * 从用户关联的角色删除一个角色
   */
  @Test
  public void manyToManyRemove() {
    User user = entityManager.find(User.class, "1");
    List<Role> roles = user.getRoles().stream().toList();
    user.getRoles().remove(roles.get(0));
    entityManager.persist(user);
  }

  @Test
  public void manyToManyByMiddle() {
    List<Menu> menuList = entityManager.createQuery("select m from Menu m", Menu.class)
        .getResultList();
    Role role = entityManager.find(Role.class, "1");
    for (Menu menu : menuList) {
      entityManager.persist(new RoleMenu().setMenu(menu).setRole(role));
    }
  }

  @Test
  public void manyToManyByMiddleQuery() {
    Role role = entityManager.find(Role.class, "1");
    log.info("角色：{}拥有的菜单数量：{}", role.getName(), role.getMenus().size());
  }

}
