package com.wontlost.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.wontlost.views.xor.XorDecView;
import com.wontlost.views.xor.XorEncView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wontlost.utils.Constant.*;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@Viewport(VIEWPORT)
@PWA(name = "XOR", shortName = "XOR")
public class MainView extends AppLayout{

    private final Tabs menu;

    public MainView() {
        this.setDrawerOpened(false);
        Span appName = new Span();
        Image portalImage = new Image("icons/banner.png", "Portal Image");
        portalImage.setWidth("120px");
        portalImage.setHeight("38px");
        appName.add(portalImage);
        appName.addClassName("hide-on-mobile");

        menu = createMenuTabs();

        this.addToNavbar(true, appName, menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
//        tabs.add(donateButton());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(TITLE_XORENC_EDITOR, VaadinIcon.FILE_CODE, XorEncView.class));
        tabs.add(createTab(TITLE_XORDEC_EDITOR, VaadinIcon.FILE_FONT, XorDecView.class));
        return tabs.toArray(new Tab[0]);
    }

//    private static Div donateButton() {
//        Div div = new Div();
//        div.getElement().setProperty("innerHTML", "<form action=\"https://www.paypal.com/cgi-bin/webscr\" " +
//                "method=\"post\" target=\"_top\" style=\"display:flex; align-items:center; height: 100%; \"> "+
//                "<input type=\"hidden\" name=\"cmd\" value=\"_s-xclick\" /> "+
//                "<input type=\"hidden\" name=\"hosted_button_id\" value=\"7GG7XSYJ4TZFQ\" /> "+
//                "<input type=\"image\" src=\"icons/money.png\" border=\"0\" name=\"submit\" style= \"width:48px; height:36px\"" +
//                " title=\"PayPal - The safer, easier way to pay online!\" alt=\"Donate with PayPal button\" />"+
//                "</form> ");
//        return div;
//    }

    private static Tab createTab(String title, VaadinIcon icon, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().orElseThrow();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

}
