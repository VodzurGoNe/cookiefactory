package org.gruzdov.cookiefactory.web.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.gruzdov.cookiefactory.entity.Cookie;
import org.gruzdov.cookiefactory.repository.CookieRepository;
import org.gruzdov.cookiefactory.web.utils.PersistenceHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vladislav Gruzdov
 */
@SpringComponent
@UIScope
public class CookieEditor extends VerticalLayout implements KeyNotifier {

    private final CookieRepository cookieRepository;

    private Cookie cookie;

    protected TextField name = new TextField("name");
    protected BigDecimalField price = new BigDecimalField("price");

    protected Button save = new Button("Save", VaadinIcon.CHECK.create());
    protected Button cancel = new Button("Cancel");
    protected Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    protected HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    protected Binder<Cookie> binder = new Binder<>(Cookie.class);

    private ChangeHandler changeHandler;

    @Autowired
    public CookieEditor(CookieRepository cookieRepository) {
        this.cookieRepository = cookieRepository;

        this.add(name, price, actions);

        binder.bindInstanceFields(this);

        this.setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        this.addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCookie(this.cookie));
        this.setVisible(false);
    }

    public final void editCookie(Cookie cookie) {
        if (cookie == null) {
            this.setVisible(false);
            return;
        }

        boolean isNew = PersistenceHelper.isNew(cookie);
        this.cookie = isNew ? cookie : cookieRepository.findById(cookie.getId()).orElse(null);

        cancel.setVisible(!isNew);

        binder.setBean(this.cookie);

        this.setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler handler) {
        changeHandler = handler;
    }

    protected void delete() {
        cookieRepository.delete(this.cookie);
        changeHandler.onChange();
    }

    protected void save() {
        cookieRepository.save(this.cookie);
        changeHandler.onChange();
    }

}
