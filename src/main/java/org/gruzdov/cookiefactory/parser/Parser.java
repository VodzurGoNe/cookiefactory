package org.gruzdov.cookiefactory.parser;

import java.io.File;

/**
 * @author Vladislav Gruzdov
 */
public interface Parser<T> {

    T parse(File file);

    boolean isApplicable(String format);

}
