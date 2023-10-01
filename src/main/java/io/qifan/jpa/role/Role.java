package io.qifan.jpa.role;


import io.qifan.jpa.BaseEntity;
import io.qifan.jpa.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;


@Entity
@Table(name = "ROLE")
@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role extends BaseEntity {

  @Column(nullable = false, length = 20, unique = true)
  @Size(min = 1, max = 20, message = "角色名称不能为空")
  private String name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private Set<RoleMenu> menus;
  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private Set<User> users;


  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Role role = (Role) o;
    return getId() != null && Objects.equals(getId(), role.getId());
  }

  @Override
  public final int hashCode() {
    return getClass().hashCode();
  }
}