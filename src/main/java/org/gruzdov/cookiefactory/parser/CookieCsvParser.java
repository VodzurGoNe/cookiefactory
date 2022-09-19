package org.gruzdov.cookiefactory.parser;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gruzdov.cookiefactory.entity.Cookie;
import org.gruzdov.cookiefactory.factory.SimpleFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Gruzdov
 */
@Slf4j
@Component(CookieCsvParser.NAME)
public class CookieCsvParser implements Parser<List<Cookie>> {

    public static final String NAME = "cookiefactory_CookieCsvParser";
    public static final String FORMAT = "csv";

    @Override
    public List<Cookie> parse(File file) {
        List<Cookie> entities = new ArrayList<>();
        try (var reader = new CSVReaderBuilder(new FileReader(file)).build()) {
            List<String[]> entries = reader.readAll();
            entries.forEach(entry -> {
                var cookie = SimpleFactory.create(Cookie.class);
                cookie.setName(entry[0].trim());
                cookie.setPrice(new BigDecimal(entry[1].trim()));

                entities.add(cookie);
            });

        } catch (CsvException | IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        return entities;
    }

    @Override
    public boolean isApplicable(String format) {
        return FORMAT.equalsIgnoreCase(format);
    }

}
