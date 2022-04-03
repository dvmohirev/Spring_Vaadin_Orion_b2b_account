package ru.orioninno.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("ShopOrionInno")
//@CssImport("./styles/views/helloworld/hello-world-view.css")
@Route("main")
public class MainPageView extends AppLayout{
    public MainPageView() {
        topMenu();
    }

    protected void topMenu() {
        H1 title = new H1("MyApp");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");

        Tab login = new Tab(new Button("Войти", e -> UI.getCurrent().navigate("login")));

        Tabs menuTabs = new Tabs(login);

        addToNavbar(title, menuTabs);
    }

}
