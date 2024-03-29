package org.example.page.loggedout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.page.BasePage;

public class FooterLoggedout extends BasePage {

    public final Locator FOOTER_CONTAINER = page.locator("div[data-elementor-type='footer'][data-elementor-id='88']");
    public FooterLoggedout(Page page) {
        super(page);
    }
}
