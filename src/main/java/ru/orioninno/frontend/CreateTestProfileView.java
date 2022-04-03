package ru.orioninno.frontend;

import ru.orioninno.entities.User;
import ru.orioninno.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("createtestprofile")
@PageTitle("Registration | Vaadin Show")
public class CreateTestProfileView extends AbstractView{
    private final UserService userService;

    public CreateTestProfileView(UserService userService) {
        this.userService = userService;
        initRegAdmin();
    }

    public void initRegAdmin(){
        Button goToLogin = new Button("Вернуться к авторизации", event -> {
            User user = userService.saveUser("89080635045", "123456", "Дмитрий", "Мохирев", "dvmohirev@yandex.ru", "26", "1000000");
            UI.getCurrent().navigate("login");
        });
        add(goToLogin);
    }
}
