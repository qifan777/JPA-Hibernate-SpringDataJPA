package io.qifan.jpa.user.dto.response;

import io.qifan.jpa.role.Role;
import java.util.Set;
import lombok.Data;

@Data
public class UserRoleResponse {

  private String nickname;
  private Set<Role> roles;

  public UserRoleResponse(String nickname, Set<Role> roles) {
    this.nickname = nickname;
    this.roles = roles;
  }

}
