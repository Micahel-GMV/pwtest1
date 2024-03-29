package org.example.page.loggedout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.page.BasePage;

public class LoginSignupPage extends BasePage {

    public final Locator AUTH_CONTAINER = locator("div.auth0-lock-widget-container");
    public final Locator AUTH_GOOGLE = locator("a.auth0-lock-social-button[data-provider='google-oauth2']");
    public final Locator LOGIN_SIGNUP_BUTTON = locator("button.auth0-lock-submit");
    public final Locator EMAIL_INPUT = locator("input.auth0-lock-input[type='email']");
    public final Locator PASSWORD_INPUT = locator("input.auth0-lock-input[type='password']");
    public final Locator CURRENT_LOCK_TAB = locator("ul.auth0-lock-tabs > li.auth0-lock-tabs-current");
    public final Locator NOTSELECTED_LOCK_TAB = locator("//ul[@class='auth0-lock-tabs']/li[not(contains(@class, 'auth0-lock-tabs-current'))]");
    public final Locator BLANK_EMAIL_HINT = locator("div#auth0-lock-error-msg-email");
    public final Locator BLANK_PASSWORD_HINT = locator("div#auth0-lock-error-msg-password");
    public final Locator AUTH_GLOBAL_MESSAGE_ERROR = locator("div.auth0-global-message.auth0-global-message-error");

    public LoginSignupPage(Page page) {
        super(page);
    }
}
