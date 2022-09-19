package org.gruzdov.cookiefactory.service;

import org.apache.commons.lang3.StringUtils;
import org.gruzdov.cookiefactory.entity.Cookie;
import org.gruzdov.cookiefactory.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Gruzdov
 */
@Service(CookieParserService.NAME)
public class CookieParserServiceImpl implements CookieParserService {

    private final Map<String, Parser<List<Cookie>>> cookieParsers;

    @Autowired
    public CookieParserServiceImpl(Map<String, Parser<List<Cookie>>> cookieParsers) {
        this.cookieParsers = cookieParsers;
    }

    @Override
    public List<Cookie> parseFile(final File file) {
        final var format = StringUtils.substringAfterLast(file.getName(), ".");
        var parser = cookieParsers.values()
                .stream()
                .filter(p -> p.isApplicable(format))
                .findFirst()
                .orElseThrow();

        return parser.parse(file);
    }

}
