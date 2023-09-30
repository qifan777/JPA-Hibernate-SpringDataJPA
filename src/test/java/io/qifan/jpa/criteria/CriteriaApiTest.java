package io.qifan.jpa.criteria;

import io.qifan.jpa.menu.Menu;
import io.qifan.jpa.menu.MenuType;
import io.qifan.jpa.menu.Menu_;
import io.qifan.jpa.role.Role;
import io.qifan.jpa.role.Role_;
import io.qifan.jpa.user.GenderType;
import io.qifan.jpa.user.User;
import io.qifan.jpa.user.UserPhonePassword;
import io.qifan.jpa.user.UserPhonePassword_;
import io.qifan.jpa.user.UserWeChat;
import io.qifan.jpa.user.UserWeChat_;
import io.qifan.jpa.user.User_;
import io.qifan.jpa.user.dto.response.UserPhonePasswordResponse;
import io.qifan.jpa.user.dto.response.UserSimpleResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.tree.domain.SqmSetJoin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CriteriaApiTest {

  @Autowired
  EntityManager entityManager;

  @Test
  public void jpaDemo() {
    entityManager.createQuery(
        "select u.id from User u left join UserWeChat uw on u.id=uw.id where uw.openId='oEheF5USRu6Y3qWjpb3wJPBfuejw' and u.phonePassword.phoneNumber like '136%'",
        String.class).getResultList().forEach(user -> log.info("用户id：{}", user));
  }

  @Test
  public void criteriaDemo() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    // 创建一个返回类型是String的criteria查询语句
    CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
    // 查询语句的root是User。from User u。
    Root<User> u = query.from(User.class);
    // Root对象可以join。由于配置关系时已经确定了外键，这步不需要写on条件。
    Join<User, UserWeChat> uw = u.join(User_.weChat);
    // criteriaBuilder用于构造相等查询条件，条件作用的路径 uw.get(UserWeChat_.openId)。
    query.where(criteriaBuilder.equal(uw.get(UserWeChat_.openId), "oEheF5USRu6Y3qWjpb3wJPBfuejw"),
        // 构造like查询条件，条件作用的路径是u.get(User_.phonePassword).get(UserPhonePassword_.phoneNumber)
        criteriaBuilder.like(u.get(User_.phonePassword).get(UserPhonePassword_.phoneNumber),
            "136%"));
    query.select(u.get(User_.id));
    entityManager.createQuery(query).getResultList().forEach(id -> {
      log.info("用户id:{}", id);
    });
  }

  /**
   * select表达式。表达式可以是简单的 u.avatar，也可以是case when，count(u.avatar)等。
   *
   * <pre>
   *     select
   *         upper(case
   *             when u.avatar is null then 'avatar is null'
   *             else u.avatar
   *         end)
   *     from
   *         user u
   * </pre>
   */
  @Test
  public void selectExpression() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
    Root<User> userRoot = query.from(User.class);
    // 构造 case when
    Expression<String> selectCase = criteriaBuilder.<String>selectCase()
        // when u.avatar is null then 'avatar is null'
        .when(criteriaBuilder.isNull(userRoot.get("avatar")), "avatar is null")
        // else u.avatar
        .otherwise(userRoot.get("avatar"));
    query.select(criteriaBuilder.upper(selectCase));
    List<String> resultList = entityManager.createQuery(query).getResultList();
    resultList.forEach(log::info);
  }

  /**
   * <pre>
   * select
   *     u1_0.nickname,
   *     p1_0.phone_number
   * from
   *     user u1_0
   * join
   *     user_phone_password p1_0
   *         on u1_0.id=p1_0.id
   * </pre>
   */
  @Test
  public void selectMultiExpression() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    // 查询的返回结果是Tuple，类似jdbc中的resultSet。Tuple比较灵活，当你不想定义返回结果时可以用Tuple。
    CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
    Root<User> userRoot = query.from(User.class);
    // userRoot.get("phonePassword").get("phoneNumber") 会自动join phonePassword。
    // 同时需要注意数据库中phoneNumber是phone_number，所以这边记得是填写实体类中的属性名称而不是数据库中的名称。
    query.multiselect(userRoot.get("nickname"), userRoot.get("phonePassword").get("phoneNumber"));
    entityManager.createQuery(query).getResultList().forEach(res -> {
      log.info("用户昵称：{}, 手机号：{}", res.get(0), res.get(1));
    });
  }

  @Test
  public void selectDto() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserSimpleResponse> query = criteriaBuilder.createQuery(UserSimpleResponse.class);
    Root<User> userRoot = query.from(User.class);
    CompoundSelection<UserSimpleResponse> construct = criteriaBuilder.construct(
        UserSimpleResponse.class,
        userRoot.get("nickname"),
        userRoot.get("phonePassword").get("phoneNumber"));
    query.select(construct);
    entityManager.createQuery(query).getResultList().forEach(res -> {
      log.info(res.toString());
    });
  }

  @Test
  public void selectDto2() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserPhonePasswordResponse> query = criteriaBuilder.createQuery(
        UserPhonePasswordResponse.class);
    Root<User> userRoot = query.from(User.class);
    Join<Object, Object> phonePasswordJoin = userRoot.join("phonePassword");
    CompoundSelection<UserPhonePasswordResponse> construct = criteriaBuilder.construct(
        UserPhonePasswordResponse.class,
        userRoot.get("nickname"),
        phonePasswordJoin
    );
    query.select(construct);
    entityManager.createQuery(query).getResultList().forEach(res -> {
      log.info(res.toString());
    });
  }


  /**
   * select u.* from user u
   */
  @Test
  public void selectRoot() {
    // 用于构造查询语句和查询条件
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    // User.class代表查询的返回结果是User类型
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    // from user
    Root<User> userRoot = query.from(User.class);
    // select u.* from user u
    query.select(userRoot);
    List<User> resultList = entityManager.createQuery(query).getResultList();
    resultList.forEach(user -> {
      log.info(user.toString());
    });
  }

  /**
   * select u.*, r.* from user u, role r
   */
  @Test
  public void queryMultiRoot() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
    // from user
    Root<User> userRoot = query.from(User.class);
    // from user, role
    Root<Role> roleRoot = query.from(Role.class);
    // select u.*, r.*
    query.multiselect(userRoot, roleRoot);
    List<Tuple> resultList = entityManager.createQuery(query).getResultList();
    resultList.forEach(res -> {
      // res.get(0) -> user, res.get(1)-> role 和 multiselect参数对应
      log.info("用户信息：{}，角色信息：{}", res.get(0), res.get(1));
    });
  }


  /**
   * <pre>
   *       select
   *         m.*
   *     from
   *         menu m
   *     where
   *         m.id!=?
   *         and m.order_num>=?
   * </pre>
   */
  @Test
  public void compare() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Menu> query = criteriaBuilder.createQuery(Menu.class);
    Root<Menu> menuRoot = query.from(Menu.class);

    // 可以忽略不写select,默认就是select menuRoot
    // query.select(menuRoot);

    // where里面可以传多个条件 多个条件是and逻辑连接.
    query.where(criteriaBuilder.notEqual(menuRoot.get(Menu_.id), "1"),
        criteriaBuilder.ge(menuRoot.get(Menu_.orderNum), 0));
    entityManager.createQuery(query).getResultList().forEach(menu -> {
      log.info("菜单: {}", menu);
    });
    // =
    criteriaBuilder.equal(menuRoot.get(Menu_.id), "1");
    // !=
    criteriaBuilder.notEqual(menuRoot.get(Menu_.id), "1");
    // ---- 下面的属性只能是数值型之间的比较 >= > <= <
    //  >= 运算符
    criteriaBuilder.ge(menuRoot.get(Menu_.orderNum), 0);
    //  > 运算符
    criteriaBuilder.gt(menuRoot.get(Menu_.orderNum), 0);
    //  <= 运算符
    criteriaBuilder.le(menuRoot.get(Menu_.orderNum), 0);
    // < 运算符
    criteriaBuilder.lt(menuRoot.get(Menu_.orderNum), 0);
    // ---- 下面的属性可以是非数值类型之间的比较 >= > <= <
    //  >= 运算符
    criteriaBuilder.greaterThanOrEqualTo(menuRoot.get(Menu_.createdAt), LocalDateTime.now());
    //  > 运算符
    criteriaBuilder.greaterThan(menuRoot.get(Menu_.createdAt), LocalDateTime.now());
    // <=
    criteriaBuilder.lessThanOrEqualTo(menuRoot.get(Menu_.createdAt), LocalDateTime.now());
    // >
    criteriaBuilder.lessThan(menuRoot.get(Menu_.createdAt), LocalDateTime.now());

  }

  /**
   * <pre>
   *       select
   *         m.*
   *     from
   *         menu m
   *     where
   *         m.order_num between ? and ?
   * </pre>
   */
  @Test
  public void between() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Menu> query = criteriaBuilder.createQuery(Menu.class);
    Root<Menu> menuRoot = query.from(Menu.class);
    // where里面可以传多个条件 多个条件是and逻辑连接.
    query.where(criteriaBuilder.between(menuRoot.get(Menu_.orderNum), 0, 2));
    entityManager.createQuery(query).getResultList().forEach(menu -> {
      log.info("菜单: {}", menu);
    });
  }

  /**
   * <pre>
   *       select
   *         m.*
   *     from
   *         menu m
   *     where
   *         m.menu_type in (?,?)
   * </pre>
   */
  @Test
  public void inAndNotIn() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Menu> query = criteriaBuilder.createQuery(Menu.class);
    Root<Menu> menuRoot = query.from(Menu.class);
    query.where(menuRoot.get(Menu_.menuType).in(MenuType.MENU, MenuType.DIRECTORY));
    entityManager.createQuery(query).getResultList().forEach(menu -> {
      log.info("菜单: {}", menu);
    });
    // 后面加一个not就是not in了
    menuRoot.get(Menu_.menuType).in(MenuType.MENU, MenuType.DIRECTORY).not();
  }

  /**
   * select m.* from menu m where m.name like '%管理%'
   */
  @Test
  public void like() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Menu> query = criteriaBuilder.createQuery(Menu.class);
    Root<Menu> menuRoot = query.from(Menu.class);
    query.where(criteriaBuilder.like(menuRoot.get(Menu_.name), "%管理%"));
    entityManager.createQuery(query).getResultList().forEach(menu -> {
      log.info("菜单:{}", menu);
    });
  }

  /**
   * <pre>
   *     select
   *         m.*
   *     from
   *         menu m
   *     where
   *         m.order_num is not null
   * </pre>
   */
  @Test
  public void isNull() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Menu> query = criteriaBuilder.createQuery(Menu.class);
    Root<Menu> menuRoot = query.from(Menu.class);
    query.where(criteriaBuilder.isNotNull(menuRoot.get(Menu_.orderNum)));
    entityManager.createQuery(query).getResultList().forEach(menu -> {
      log.info("菜单:{}", menu);
    });

  }


  /**
   * <pre>
   *     select
   *         u.*
   *     from
   *         user u
   *     where
   *         (
   *             u.nickname=?
   *             or u.nickname=?
   *         )
   *         and u.avatar not like ? escape ''
   *         and u.gender in (?,?)
   * </pre>
   */
  @Test
  public void logical() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> userRoot = query.from(User.class);
    //           (
    //              u.nickname=?
    //              or u.nickname=?
    //          )
    query.where(criteriaBuilder.or(criteriaBuilder.equal(userRoot.get(User_.nickname), "起凡"),
            criteriaBuilder.equal(userRoot.get(User_.nickname), "默认用户")),
        // and u.avatar not like ? escape ''
        criteriaBuilder.not(criteriaBuilder.like(userRoot.get(User_.avatar), "%https%")),
        // and u.gender in (?,?)
        userRoot.get(User_.gender).in(GenderType.MALE, GenderType.FEMALE));
    entityManager.createQuery(query).getResultList().forEach(res -> {
      log.info(res.toString());
    });
  }

  /**
   * <pre>
   *       select
   *         u.*
   *     from
   *         user u
   *     left join
   *         user_phone_password p
   *             on u.id=p.id
   *     where
   *         p.phone_number=?
   * </pre>
   */
  @Test
  public void join() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> userRoot = query.from(User.class);

    // 因为在配置关系的时候已经确定好外键了所以这边会自动生成on条件
    //      left join
    //          user_phone_password p
    //              on u.id=p.id
    Join<User, UserPhonePassword> phonePasswordJoin = userRoot.join(User_.phonePassword,
        JoinType.LEFT);
    query.where(criteriaBuilder.equal(phonePasswordJoin.get(UserPhonePassword_.phoneNumber),
        "13656987996"));
    entityManager.createQuery(query).getResultList().forEach(user -> {
      log.info("用户：{}", user);
    });
  }

  /**
   * select u.id from user u join user_role r on u.id=r.user_id group by u.id having count(u.id)>=?
   */
  @Test
  public void groupByHaving() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> userRoot = query.from(User.class);
    userRoot.join(User_.roles);
    query.groupBy(userRoot.get(User_.id));
    query.having(criteriaBuilder.ge(criteriaBuilder.count(userRoot.get(User_.id)), 2));
    entityManager.createQuery(query).getResultList().forEach(user -> {
      log.info("user:{}", user);
    });
  }

  /**
   * <pre>
   *     select
   *         u.*
   *     from
   *         user u
   *     order by
   *         (select
   *             count(1)
   *         from
   *             user_role r
   *         where
   *             u.id=r.user_id) desc
   * </pre>
   */
  @Test
  public void orderBy() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> userRoot = query.from(User.class);
    query.orderBy(criteriaBuilder.desc(criteriaBuilder.size(userRoot.get(User_.roles))));
    entityManager.createQuery(query).getResultList().forEach(user -> {
      log.info("user:{}", user);
    });
  }

  /**
   * <pre>
   *       select
   *         u.*,
   *         p.*,
   *         r.*
   *     from
   *         user u
   *     join
   *         user_phone_password p
   *             on u.id=p.id
   *     join
   *         (user_role ur
   *     join
   *         role r
   *             on r.id=ur.role_id)
   *                 on u.id=ur.user_id
   *         where
   *             ur.role_id in ((select
   *                 r2.id
   *             from
   *                 role r2
   *             where
   *                 r2.id!=?))
   * </pre>
   */
  @Test
  public void subQuery() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> userRoot = query.from(User.class);
    SqmSetJoin<User, Role> fetch = (SqmSetJoin<User, Role>) userRoot.fetch(User_.roles);

    // 子查询的返回结果是String
    Subquery<String> subquery = query.subquery(String.class);
    // from role r
    Root<Role> roleRoot = subquery.from(Role.class);
    // where r.id!=1
    subquery.where(criteriaBuilder.notEqual(roleRoot.get(Role_.id), "1"));
    subquery.select(roleRoot.get(Role_.id));
    query.where(fetch.get(Role_.id).in(subquery));
    query.select(userRoot);
    entityManager.createQuery(query).getResultList().forEach(user -> {
      log.info("user: {}，roles：{}", user, user.getRoles());
    });
  }


  @Test
  public void queryRootReturnTuple() {

  }
}
