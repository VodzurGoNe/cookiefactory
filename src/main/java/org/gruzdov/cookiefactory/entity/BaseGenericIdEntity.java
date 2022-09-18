package org.gruzdov.cookiefactory.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.time.Instant;
import java.util.Objects;

/**
 * @author Vladislav Gruzdov
 */
@MappedSuperclass
public abstract class BaseGenericIdEntity<T> implements Entity<T> {

    private static final long serialVersionUID = -8400641366148656527L;

    @Transient
    protected byte __state = BaseEntityInternalAccess.UNDEFINED;

    public BaseGenericIdEntity() {
    }

    public abstract void setId(T id);

    @Version
    @Column(name = "VERSION", nullable = false)
    protected Integer version;

    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    protected Instant lastModifiedDate;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        //noinspection unchecked
        return Objects.equals(getId(), ((BaseGenericIdEntity<T>) other).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
