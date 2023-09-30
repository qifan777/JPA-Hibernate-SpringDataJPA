package io.qifan.jpa.address;

import io.qifan.jpa.BaseEntity;
import io.qifan.jpa.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Accessors(chain = true)
@Table(name = "ADDRESS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Address extends BaseEntity {

  // 门牌号
  private String houseNumber;
  // 地址详情
  private String details;
  // 街道/区
  private String district;
  // 城市
  private String city;
  // 省份
  private String province;
  // 维度
  private Double latitude;
  // 经度
  private Double longitude;
  // 手机号
  private String phoneNumber;
  // 姓名
  private String realName;
  // 地址创建人
  @ManyToOne
  private User user;

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
    Address address = (Address) o;
    return getId() != null && Objects.equals(getId(), address.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
