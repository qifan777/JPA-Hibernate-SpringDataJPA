package io.qifan.jpa.model;

import io.qifan.jpa.address.Address;
import io.qifan.jpa.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

  @Test
  public void manyToOneTest() {
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
}
