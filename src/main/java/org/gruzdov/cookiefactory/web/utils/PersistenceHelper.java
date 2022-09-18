package org.gruzdov.cookiefactory.web.utils;

import org.gruzdov.cookiefactory.entity.BaseEntityInternalAccess;
import org.gruzdov.cookiefactory.entity.BaseGenericIdEntity;

/**
 * @author Vladislav Gruzdov
 */
public final class PersistenceHelper {

    private PersistenceHelper() {
    }

    public static boolean isNew(BaseGenericIdEntity<?> entity) {
        return BaseEntityInternalAccess.isNew(entity);
    }

}
