package ru.orioninno.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jdk.jfr.Event;

@Route("login")
@PageTitle("Login | B2B Personal account")
public class LoginView extends VerticalLayout implements BeforeEnterObserver{
    private LoginForm login = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        Button registrationButton = new Button("Регистрация", event -> {
            UI.getCurrent().navigate("registration");
        });
        Button createTestProfile = new Button("Создать тестового пользователя", event -> {
            UI.getCurrent().navigate("createtestprofile");
        });
        registrationButton.setSizeUndefined();
        add(new H1("B2B Personal account"), login, registrationButton, createTestProfile);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
