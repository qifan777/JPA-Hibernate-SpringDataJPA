package io.qifan.jpa.role;

import io.qifan.jpa.BaseEntity;
import io.qifan.jpa.menu.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table(name = "ROLE_MENU")
@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoleMenu extends BaseEntity {

  @ManyToOne
  private Menu menu;
  @ManyToOne
  private Role role;
}
