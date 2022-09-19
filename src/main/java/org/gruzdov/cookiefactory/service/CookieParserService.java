package org.gruzdov.cookiefactory.service;

import org.gruzdov.cookiefactory.entity.Cookie;

import java.io.File;
import java.util.List;

/**
 * @author Vladislav Gruzdov
 */
public interface CookieParserService {

    String NAME = "cookiefactory_CookieParserService";

    List<Cookie> parseFile(final File file);

}
