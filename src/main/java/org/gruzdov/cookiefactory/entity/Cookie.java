package org.gruzdov.cookiefactory.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author Vladislav Gruzdov
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "COOKIE")
public class Cookie extends BaseUuidEntity {

    private static final long serialVersionUID = -8400642366148656527L;

    @NotBlank(message = "Price is required field")
    @Column(name = "NAME")
    protected String name;

    @NotNull(message = "Price is required field")
    @Column(name = "PRICE")
    protected BigDecimal price;

}
