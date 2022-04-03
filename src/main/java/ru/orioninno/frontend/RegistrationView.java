package ru.orioninno.frontend;

import com.vaadin.flow.component.html.H1;
import ru.orioninno.entities.User;
import ru.orioninno.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("registration")
@PageTitle("Registration | Vaadin Show")
public class RegistrationView extends AbstractView {
    private final UserService userService;

    public RegistrationView(UserService userService) {
        this.userService = userService;
        initRegistrationView();
    }

    private void initRegistrationView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField phoneTextField = initTextFieldWithPlaceholder("Номер телефона");
        TextField passwordTextField = initTextFieldWithPlaceholder("Пароль");
        TextField firstNameTextField = initTextFieldWithPlaceholder("Имя");
        TextField lastNameTextField = initTextFieldWithPlaceholder("Фамилия");
        TextField emailTextField = initTextFieldWithPlaceholder("Email");
        TextField ageTextField = initTextFieldWithPlaceholder("Возраст");
        TextField moneyTextField = initTextFieldWithPlaceholder("Деньги");
        //Тестируем добавление роли при регистрации
        //TextField roleTextField = initTextFieldWithPlaceholder("Роль");
        Button registrationButton = new Button("Зарегистрироваться", event -> {
            boolean hasError = false;

            if(!phoneTextField.getValue().matches("\\d+")) {
                Notification.show("Телефон должен состоять из цифр");
                hasError = true;
            }

            if(!firstNameTextField.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Имя должно состоять из букв");
                hasError = true;
            }

            if(!lastNameTextField.getValue().matches("[а-яА-Я]+")) {
                Notification.show("фамилия должно состоять из букв");
                hasError = true;
            }

            if(!ageTextField.getValue().matches("\\d+") || Integer.parseInt(ageTextField.getValue()) < 18) {
                Notification.show("Возраст должен состоять из цифр и вы должны быть старше 18 лет");
                hasError = true;
            }
            /*if(!roleTextField.getValue().equals(RoleDto.CUSTOMER) || !roleTextField.getValue().equals(RoleDto.MANAGER)
            || !roleTextField.getValue().equals(RoleDto.ADMIN)) {
                Notification.show("Роли могут быть только: CUSTOMER, MANAGER, ADMIN");
                hasError = true;
            }*/

            if(hasError) {
                Notification.show("Ошибка при регистрации нового пользователя. \n " +
                        "Проверьте уникальность данных Телефон");
                return;
            } else {
                User user = userService.saveUser(phoneTextField.getValue(), passwordTextField.getValue(), firstNameTextField.getValue(),
                        lastNameTextField.getValue(), emailTextField.getValue(), ageTextField.getValue(), moneyTextField.getValue()/*, roleTextField.getValue()*/);

                UI.getCurrent().navigate("login");
            }
        });
        Button backButton = new Button("Назад", event -> UI.getCurrent().navigate("login"));
        add(new H1("Registration new user"), phoneTextField, passwordTextField, firstNameTextField, lastNameTextField, emailTextField, ageTextField, moneyTextField, registrationButton, backButton);
    }
}
