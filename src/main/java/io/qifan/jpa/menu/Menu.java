package io.qifan.jpa.menu;


import io.qifan.jpa.BaseEntity;
import io.qifan.jpa.role.RoleMenu;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

  // 路由路径
  private String path;

  @Convert(converter = MenuTypeConverter.class)
  @Column(nullable = false)
  private MenuType menuType;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private List<RoleMenu> roles;

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
