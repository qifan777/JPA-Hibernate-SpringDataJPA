package io.qifan.jpa.user.dto.response;

import lombok.Data;

@Data
public class UserSimpleResponse {

  private String nickname;
  private String phoneNumber;

  public UserSimpleResponse(String nickname, String phoneNumber) {
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
  }
}
