package org.example.page.loggedout;

import com.microsoft.playwright.Page;
import lombok.Getter;
import org.example.page.BasePage;

@Getter
public class HomePageLoggedOut extends BasePage {

    private final FooterLoggedout footer;
    private final HeaderLoggedout header;
    private final ContentLoggedout content;
    public HomePageLoggedOut(Page page) {
        super(page);
        footer = new FooterLoggedout(page);
        header = new HeaderLoggedout(page);
        content = new ContentLoggedout(page);
    }
}
