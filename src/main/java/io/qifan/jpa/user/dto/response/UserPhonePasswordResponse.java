package io.qifan.jpa.user.dto.response;

import io.qifan.jpa.user.UserPhonePassword;
import lombok.Data;

@Data
public class UserPhonePasswordResponse {

  private String nickname;
  private String phoneNumber;
  private String password;

  public UserPhonePasswordResponse(String nickname, UserPhonePassword phonePassword) {
    this.nickname = nickname;
    this.phoneNumber = phonePassword.getPhoneNumber();
    this.password = phonePassword.getPassword();
  }
}
