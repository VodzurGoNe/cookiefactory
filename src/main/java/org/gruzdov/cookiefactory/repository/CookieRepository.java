package org.gruzdov.cookiefactory.repository;

import org.gruzdov.cookiefactory.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Vladislav Gruzdov
 */
@Repository
public interface CookieRepository extends JpaRepository<Cookie, UUID> {

    List<Cookie> findByPriceBetween(BigDecimal start, BigDecimal end);

}
