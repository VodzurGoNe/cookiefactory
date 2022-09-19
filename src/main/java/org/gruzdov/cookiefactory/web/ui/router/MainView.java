package org.gruzdov.cookiefactory.web.ui.router;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gruzdov.cookiefactory.entity.Cookie;
import org.gruzdov.cookiefactory.factory.SimpleFactory;
import org.gruzdov.cookiefactory.repository.CookieRepository;
import org.gruzdov.cookiefactory.service.CookieParserService;
import org.gruzdov.cookiefactory.web.ui.editor.CookieEditor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Vladislav Gruzdov
 */
@Log4j2
@Route("/app")
public class MainView extends VerticalLayout {

    protected final Grid<Cookie> grid;

    protected final TextField filter;

    private final CookieRepository cookieRepository;

    private final CookieParserService cookieParserService;

    private final CookieEditor cookieEditor;

    protected MemoryBuffer memoryBuffer;

    private final Button addNewBtn;

    private File tempFile;

    @Autowired
    public MainView(CookieRepository cookieRepository, CookieParserService cookieParserService,
                    CookieEditor cookieEditor) {
        this.cookieRepository = cookieRepository;
        this.cookieParserService = cookieParserService;
        this.cookieEditor = cookieEditor;
        this.grid = new Grid<>(Cookie.class);
        this.filter = new TextField();
        this.memoryBuffer = new MemoryBuffer();
        this.addNewBtn = new Button("New cookie", VaadinIcon.PLUS.create());

        var actions = new HorizontalLayout(filter, addNewBtn);
        this.add(actions, grid, cookieEditor);

        grid.setHeight("300px");
        grid.setColumns("name", "price");

        filter.setPlaceholder("Filter by price (100-500)");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> cookieEditor.editCookie(e.getValue()));

        addNewBtn.addClickListener(e -> {
            var cookie = SimpleFactory.create(Cookie.class);
            cookie.setName("Cookies for coffee");
            cookie.setPrice(new BigDecimal("3.50"));

            cookieEditor.editCookie(cookie);
        });

        cookieEditor.setChangeHandler(() -> {
            cookieEditor.setVisible(false);
            listCustomers(filter.getValue());
        });

        listCustomers(null);

        var upload = new Upload((Receiver) (fileName, mimeType) -> {
            try {
                tempFile = File.createTempFile("temp", ".csv");
                return new FileOutputStream(tempFile);
            } catch (IOException e) {
                log.error(ExceptionUtils.getStackTrace(e));
                return null;
            }
        });

        upload.addSucceededListener(event -> {
            parseFile(tempFile);
            grid.setItems(cookieRepository.findAll());
        });

        upload.setAcceptedFileTypes(".csv");

        actions.add(upload);
    }

    protected void listCustomers(String filterText) {
        if (StringUtils.isBlank(filterText)) {
            grid.setItems(cookieRepository.findAll());
        } else {
            String[] range = filterText.split("-");
            if (range.length == 2) {
                Collection<Cookie> cookies =
                        cookieRepository.findByPriceBetween(new BigDecimal(range[0]), new BigDecimal(range[1]));
                grid.setItems(cookies);
            }

        }

    }

    public CookieEditor getCookieEditor() {
        return cookieEditor;
    }

    protected void parseFile(File file) {
        List<Cookie> entities = cookieParserService.parseFile(file);
        cookieRepository.saveAll(new LinkedHashSet<>(entities));
    }

}
