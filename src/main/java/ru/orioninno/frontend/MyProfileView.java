package ru.orioninno.frontend;

import ru.orioninno.entities.User;
import ru.orioninno.repositories.UserRepository;
import ru.orioninno.security.CustomPrincipal;
import ru.orioninno.security.SecurityUtils;
import ru.orioninno.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route("profile")
public class MyProfileView extends AbstractView{
    protected final UserService userService;
    private final UserRepository userRepository;
    private final CustomPrincipal principal;

    protected Grid<User> userGrid;

    public MyProfileView(UserService userService,
                     UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.principal = SecurityUtils.getPrincipal();
        initMyProfile();
    }

    public void initMyProfile() {
        TopMenuView topMenu = new TopMenuView();
        add(topMenu.initTopMenu());
        userGrid = new Grid<>(User.class);
        userGrid.setWidth("100%");
        userGrid.setColumns("id", "password", "phone", "firstName", "lastName", "email", "age", "money");
        userGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        User userProfile = userService.findByUsername(principal.getUsername());
        userGrid.setItems(userProfile);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, userGrid);
        add(userGrid);

        Button backButton = new Button("Назад", event -> UI.getCurrent().navigate("market"));
        add(/*userGrid, */backButton);
    }
}
