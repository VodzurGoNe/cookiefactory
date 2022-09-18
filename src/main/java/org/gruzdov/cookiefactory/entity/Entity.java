package org.gruzdov.cookiefactory.entity;

import java.io.Serializable;

/**
 * @author Vladislav Gruzdov
 */
public interface Entity<T> extends Serializable {

    T getId();

}
