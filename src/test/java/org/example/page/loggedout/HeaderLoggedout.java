package org.example.page.loggedout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.page.BasePage;

public class HeaderLoggedout extends BasePage {

    public final Locator HEADER_CONTAINER = page.locator("div[data-elementor-type='header'][data-elementor-id='9']");
    public final Locator LOGO = HEADER_CONTAINER.locator("div[data-widget_type=\"image.default\"] img[nitro-lazy-src*='Howwe_Logo_Orange_CMYK-ai.png']");
    public final Locator MENU = HEADER_CONTAINER.locator("ul#mega-menu-menu-1.mega-menu.max-mega-menu.mega-menu-horizontal");
    public final Locator MENU_ITEMS = locator("ul#mega-menu-menu-1.mega-menu.max-mega-menu.mega-menu-horizontal > li.mega-menu-item > a");
    public final Locator MENU_ITEM_SUBMENU = locator("//ul[@class='mega-sub-menu']//img");//TODO: change to locator

    public final Locator LOGIN_BUTTON = MENU_ITEMS.getByText("Login");
    public HeaderLoggedout(Page page) {
        super(page);
    }

    public Locator getMenuItemByText(String text) {
        return MENU_ITEMS.getByText(text);
    }

    public Locator getSubmenuItemsByItemText(String text) {
        return getMenuItemByText(text).locator("xpath=..").locator(MENU_ITEM_SUBMENU);
    }

    public LoginSignupPage clickLogin() {
        LOGIN_BUTTON.click();
        LoginSignupPage loginSignupPage = new LoginSignupPage(page);
        loginSignupPage.LOGIN_SIGNUP_BUTTON.waitFor();
        return loginSignupPage;
    }
}
