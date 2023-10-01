package io.qifan.jpa.user;


import io.qifan.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;


@Table(name = "USER_PHONE_PASSWORD")
@Entity
@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserPhonePassword extends BaseEntity {

  @OneToOne
  // 将user.id映射到this.id
  @MapsId
  // id既是主键又是外键
  @JoinColumn(name = "id")
  @ToString.Exclude
  private User user;
  @NaturalId
  private String phoneNumber;
  private String password;


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
    UserPhonePassword that = (UserPhonePassword) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return getClass().hashCode();
  }
}
