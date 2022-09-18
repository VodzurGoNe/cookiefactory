package org.gruzdov.cookiefactory.entity;

import org.gruzdov.cookiefactory.utils.UuidProvider;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Vladislav Gruzdov
 */
@MappedSuperclass
public abstract class BaseUuidEntity extends BaseGenericIdEntity<UUID> implements HasUuid {

    private static final long serialVersionUID = -2217624132287086977L;

    @Id
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    protected UUID id;

    public BaseUuidEntity() {
        id = UuidProvider.createUuid();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getUuid() {
        return id;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

}
