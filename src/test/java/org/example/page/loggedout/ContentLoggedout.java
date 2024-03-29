package org.example.page.loggedout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.page.BasePage;

public class ContentLoggedout extends BasePage {

    public final Locator CONTENT_CONTAINER = page.locator("div[data-elementor-type=\"wp-page\"]");
    public ContentLoggedout(Page page) {
        super(page);
    }
}
