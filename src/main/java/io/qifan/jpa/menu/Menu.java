package io.qifan.jpa.menu;


import io.qifan.jpa.BaseEntity;
import io.qifan.jpa.role.RoleMenuRel;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.proxy.HibernateProxy;


@Entity
@Table(name = "MENU")
@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicInsert
public class Menu extends BaseEntity {

  @Column(length = 20, nullable = false, unique = true)
  private String name;

  @Column
  @ColumnDefault("0")
  private String parentId;

  @Column
  @ColumnDefault("0")
  private Integer orderNum;

  private String path;

  private String component;

  @Convert(converter = MenuTypeConverter.class)
  @Column(nullable = false)
  @NotNull(message = "菜单类型不能为空")
  private MenuType menuType;

  @Column(nullable = false)
  @Size(min = 1, max = 255, message = "权限标识不能为空")
  private String perms;

  private String icon;
  @OneToMany(mappedBy = "menu")
  @Exclude
  private List<RoleMenuRel> roles;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Menu menu = (Menu) o;
    return getId() != null && Objects.equals(getId(), menu.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
