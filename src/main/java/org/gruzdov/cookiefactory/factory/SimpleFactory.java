package org.gruzdov.cookiefactory.factory;

import org.gruzdov.cookiefactory.entity.BaseEntityInternalAccess;
import org.gruzdov.cookiefactory.entity.BaseGenericIdEntity;
import org.gruzdov.cookiefactory.entity.Entity;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Vladislav Gruzdov
 */
public class SimpleFactory {

    public static <T extends Entity<?>> T create(Class<T> entityClass) {
        try {
            if (BaseGenericIdEntity.class.isAssignableFrom(entityClass)) {
                return (T) createBaseGenericIdEntity(entityClass);
            }

            return entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {

            e.printStackTrace();
            throw new RuntimeException("Unable to create entity instance", e);
        }

    }

    private static <T extends Entity<?>> T createBaseGenericIdEntity(Class<T> entityClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var entity = entityClass.getDeclaredConstructor().newInstance();
        BaseEntityInternalAccess.setNew((BaseGenericIdEntity<?>) entity, true);

        return entity;
    }

}
