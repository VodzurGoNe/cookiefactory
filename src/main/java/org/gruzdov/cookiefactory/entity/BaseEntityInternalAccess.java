package org.gruzdov.cookiefactory.entity;

/**
 * @author Vladislav Gruzdov
 */
public final class BaseEntityInternalAccess {

    public static final int NEW = 1;
    public static final int DETACHED = 2;
    public static final int MANAGED = 4;
    public static final int REMOVED = 8;
    public static final int UNDEFINED = 100;

    private BaseEntityInternalAccess() {
    }

    public static boolean isNew(BaseGenericIdEntity<?> entity) {
        return (entity.__state & NEW) == NEW;
    }

    public static void setNew(BaseGenericIdEntity<?> entity, boolean _new) {
        entity.__state = (byte) (_new ? entity.__state | NEW : entity.__state & ~NEW);
    }

    public static boolean isManaged(BaseGenericIdEntity<?> entity) {
        return (entity.__state & MANAGED) == MANAGED;
    }

    public static void setManaged(BaseGenericIdEntity<?> entity, boolean managed) {
        entity.__state = (byte) (managed ? entity.__state | MANAGED : entity.__state & ~MANAGED);
    }

    public static boolean isDetached(BaseGenericIdEntity<?> entity) {
        return (entity.__state & DETACHED) == DETACHED;
    }

    public static void setDetached(BaseGenericIdEntity<?> entity, boolean detached) {
        entity.__state = (byte) (detached ? entity.__state | DETACHED : entity.__state & ~DETACHED);
    }

    public static boolean isRemoved(BaseGenericIdEntity<?> entity) {
        return (entity.__state & REMOVED) == REMOVED;
    }

    public static void setRemoved(BaseGenericIdEntity<?> entity, boolean removed) {
        entity.__state = (byte) (removed ? entity.__state | REMOVED : entity.__state & ~REMOVED);
    }

}
