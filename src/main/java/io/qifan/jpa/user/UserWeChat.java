package io.qifan.jpa.user;


import io.qifan.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;


@Entity
@Table(name = "USER_WECHAT")
@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserWeChat extends BaseEntity {

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  @ToString.Exclude
  private User user;

  @Column(length = 30, unique = true, nullable = false)
  @NotBlank(message = "openId 不能为空")
  private String openId;
  private String qrCode;

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
    UserWeChat that = (UserWeChat) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return getClass().hashCode();
  }
}