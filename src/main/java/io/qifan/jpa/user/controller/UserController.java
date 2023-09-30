package io.qifan.jpa.user.controller;

import io.qifan.jpa.user.User;
import io.qifan.jpa.user.dto.response.UserSimpleResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

  private final EntityManager entityManager;

  @GetMapping("{id}")
  public UserSimpleResponse findById(@PathVariable String id) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserSimpleResponse> query = criteriaBuilder.createQuery(UserSimpleResponse.class);
    Root<User> userRoot = query.from(User.class);
    CompoundSelection<UserSimpleResponse> construct = criteriaBuilder.construct(
        UserSimpleResponse.class,
        userRoot.get("nickname"),
        userRoot.get("phonePassword").get("phoneNumber"));
    query.where(criteriaBuilder.equal(userRoot.get("id"),id));
    query.select(construct);
    return entityManager.createQuery(query).getSingleResult();

  }
}
