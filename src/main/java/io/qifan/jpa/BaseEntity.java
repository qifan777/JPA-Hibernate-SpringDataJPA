package io.qifan.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;


@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity extends AbstractAggregateRoot<BaseEntity> {

  // 主键
  @Id
  // UUID生成策略
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  // 创建时间不允许修改，且不能为空
  @Column(name = "created_at", nullable = false, updatable = false)
  // 创建时自动填写当前时间
  @CreationTimestamp
  private LocalDateTime createdAt;
  // 更新时间
  @Column(name = "updated_at", nullable = false)
  // 更新时自动填写当前时间
  @UpdateTimestamp
  private LocalDateTime updatedAt;
  // 乐观锁并发控制
  @Version
  @Column(name = "version")
  private Integer version;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    BaseEntity that = (BaseEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
