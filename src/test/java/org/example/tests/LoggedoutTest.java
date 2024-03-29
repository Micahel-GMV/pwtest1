package org.example.tests;

import com.microsoft.playwright.ConsoleMessage;
import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.base.PageWithResponse;
import org.example.config.PropertiesReader;
import org.example.page.loggedout.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoggedoutTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoggedoutTest.class);

    @DataProvider(name = "menuVariantsDataProvider")
    public Object[][] provideMenuData() {
        return new Object[][] {
                { "", "", "", "Home - Howwe" },
                { "Product", "", "enterprise-execution-software-product/", "Pioneering Enterprise Execution Software - Howwe" },
                { "Product", "HOWWE", "enterprise-execution-software-product/", "Pioneering Enterprise Execution Software - Howwe" },
                { "Product", "GETTING STARTED", "getting-started-with-howwe/", "Getting Started With Howwe - Howwe Technologies" },
                { "Resources", "CEO FORUM", "ceo-forum/", "Inspiring CEO Forum - Howwe Technologies" },
                { "Partners", "", "strategic-initiative-software-partners/", "Strategic Initiative Software Howwe - Partnership Program" }
        };
    }

    @Test
    public void loginSignupPageTest() {
        logger.info("Login/Signup page test START.");

        SoftAssert softAssert = new SoftAssert();

        PageWithResponse pageWithResponse = openPageConsented(PropertiesReader.getProperty("baseURL"), HomePageLoggedOut.class);
        HomePageLoggedOut homePage = (HomePageLoggedOut) pageWithResponse.getPage();
        LoginSignupPage loginSignupPage = homePage
                .getHeader()
                .clickLogin();

        softAssert.assertEquals(loginSignupPage.CURRENT_LOCK_TAB.textContent(),"Log In", "There is something wrong with lock tabs and their selection after opening the auth page.");
        softAssert.assertEquals(loginSignupPage.NOTSELECTED_LOCK_TAB.textContent(),"Sign Up", "There is something wrong with lock tabs and their selection after opening the auth page.");
        softAssert.assertTrue(loginSignupPage.AUTH_CONTAINER.isVisible(), "Authentication container is not visible.");
        softAssert.assertTrue(loginSignupPage.AUTH_GOOGLE.isVisible(), "Google oauth signin button is not visible.");
        softAssert.assertEquals(loginSignupPage.AUTH_GOOGLE.textContent(), "Sign in with Google", "Wrong Google button text.");
        softAssert.assertTrue(loginSignupPage.EMAIL_INPUT.isVisible(), "Email input isn`t visible.");
        softAssert.assertTrue(loginSignupPage.PASSWORD_INPUT.isVisible(), "Password input isn`t visible.");
        softAssert.assertEquals(loginSignupPage.LOGIN_SIGNUP_BUTTON.textContent(), "Log In", "Wrong text on login button.");

        loginSignupPage.NOTSELECTED_LOCK_TAB.click();

        softAssert.assertEquals(loginSignupPage.NOTSELECTED_LOCK_TAB.textContent(),"Log In", "There is something wrong with lock tabs and their selection after selecting Sign Up tab.");
        softAssert.assertEquals(loginSignupPage.CURRENT_LOCK_TAB.textContent(),"Sign Up", "There is something wrong with lock tabs and their selection after selecting Sign Up tab.");
        softAssert.assertTrue(loginSignupPage.AUTH_CONTAINER.isVisible(), "Authentication container is not visible after selecting Sign Up tab.");
        softAssert.assertTrue(loginSignupPage.AUTH_GOOGLE.isVisible(), "Google oauth signin button is not visible after selecting Sign Up tab.");
        softAssert.assertEquals(loginSignupPage.AUTH_GOOGLE.textContent(), "Sign up with Google", "Wrong Google button text after selecting Sign Up tab.");
        softAssert.assertTrue(loginSignupPage.EMAIL_INPUT.isVisible(), "Email input isn`t visible after selecting Sign Up tab.");
        softAssert.assertTrue(loginSignupPage.PASSWORD_INPUT.isVisible(), "Password input isn`t visible after selecting Sign Up tab.");
        softAssert.assertEquals(loginSignupPage.LOGIN_SIGNUP_BUTTON.textContent(), "Sign Up", "Wrong text on signup button after selecting Sign Up tab..");

        softAssert.assertAll();
        logger.info("Login/Signup page test END.");
    }

    @Test
    public void wrongLoginTest() {
        logger.info("Wrong login test START.");

        String testEmail = "testemail@testemail.com";
        String testPassword = "123456";

        SoftAssert softAssert = new SoftAssert();

        PageWithResponse pageWithResponse = openPageConsented(PropertiesReader.getProperty("baseURL"), HomePageLoggedOut.class);
        HomePageLoggedOut homePage = (HomePageLoggedOut) pageWithResponse.getPage();
        LoginSignupPage loginSignupPage = homePage
                .getHeader()
                .clickLogin();

        softAssert.assertEquals(loginSignupPage.EMAIL_INPUT.textContent(), "", "Email isn`t empty by default");
        softAssert.assertEquals(loginSignupPage.PASSWORD_INPUT.textContent(), "", "Password isn`t empty by default");
        softAssert.assertTrue(loginSignupPage.BLANK_EMAIL_HINT.isHidden(), "Blank email error message is visible before pressing Log In button");
        softAssert.assertTrue(loginSignupPage.BLANK_PASSWORD_HINT.isHidden(), "Blank password error message is visible before pressing Log In button");
        softAssert.assertTrue(loginSignupPage.AUTH_GLOBAL_MESSAGE_ERROR.isHidden(), "Global error message is visible before pressing Log In button");


        loginSignupPage.LOGIN_SIGNUP_BUTTON.click();

        softAssert.assertTrue(loginSignupPage.BLANK_EMAIL_HINT.isVisible(), "Blank email error message didn`t appear after pressing the Log In button without filling the fields.");
        softAssert.assertTrue(loginSignupPage.BLANK_PASSWORD_HINT.isVisible(), "Blank password error message didn`t appear after pressing the Log In button without filling the fields");
        softAssert.assertTrue(loginSignupPage.AUTH_GLOBAL_MESSAGE_ERROR.isHidden(), "Global error message is visible before pressing Log In button");


        loginSignupPage.EMAIL_INPUT.fill(testEmail);
        loginSignupPage.PASSWORD_INPUT.fill(testPassword);
        softAssert.assertEquals(loginSignupPage.EMAIL_INPUT.inputValue(), testEmail, "Email input does not store input value correctly");
        softAssert.assertEquals(loginSignupPage.PASSWORD_INPUT.inputValue(), testPassword, "Password input does not store input value correctly");

        loginSignupPage.LOGIN_SIGNUP_BUTTON.click();

        softAssert.assertTrue(loginSignupPage.BLANK_EMAIL_HINT.isHidden(), "Blank email error message appeared after pressing the Log In button while fields were filled.");
        softAssert.assertTrue(loginSignupPage.BLANK_PASSWORD_HINT.isHidden(), "Blank password error message appeared after pressing the Log In button while fields were filled.");
        loginSignupPage.AUTH_GLOBAL_MESSAGE_ERROR.waitFor();
        softAssert.assertTrue(loginSignupPage.AUTH_GLOBAL_MESSAGE_ERROR.isVisible(), "Global error message is not visible after pressing Log In button while fields were filled.");
        //softAssert.assertEquals(loginSignupPage.AUTH_GLOBAL_MESSAGE_ERROR.textContent(), "Wrong email or password.", "Wrong global error message after wrong email/password input.");
        //TODO: implement testcase with blocking and randomize email for this case.
        //Expected :Wrong email or password.
        //Actual   :Your account has been blocked after multiple consecutive login attempts.

        softAssert.assertAll();
        logger.info("Wrong login test END.");
    }

    @Test
    public void menuHoverTest() {
        logger.info("Hover over the menu item test START.");
        String menuItem = "Product";
        SoftAssert softAssert = new SoftAssert();
        PageWithResponse pageWithResponse = openPageConsented(PropertiesReader.getProperty("baseURL"), HomePageLoggedOut.class);
        HomePageLoggedOut homePage = (HomePageLoggedOut) pageWithResponse.getPage();
        HeaderLoggedout header = homePage.getHeader();

        Locator productMenuItem = header.getMenuItemByText(menuItem);

        softAssert.assertFalse(header.getSubmenuItemsByItemText(menuItem).nth(0).isVisible(),
                "Product menu item submenu is visible while is not hovered over.");

        productMenuItem.hover(new Locator.HoverOptions().setTrial(true));

        softAssert.assertTrue(header.getSubmenuItemsByItemText("Product").nth(0).isVisible(),
                "Product menu item submenu is not visible after been hovered over.");

        softAssert.assertAll();
        logger.info("Hover over the menu item test END.");
    }

    @Test(dataProvider = "menuVariantsDataProvider")
    public void openHomePageTest(String menuItemName, String submenuItemName, String url, String pageTitle) {
        String baseUrl = PropertiesReader.getProperty("baseURL");
        String fullUrl = baseUrl + url;
        logger.info("Open page test START. Page is " + menuItemName + " - " + submenuItemName + ". URL = " + fullUrl);
        SoftAssert softAssert = new SoftAssert();

        PageWithResponse pageWithResponse = openPageConsented(fullUrl, HomePageLoggedOut.class);

        HomePageLoggedOut homePage = (HomePageLoggedOut) pageWithResponse.getPage();
        HeaderLoggedout header = homePage.getHeader();
        FooterLoggedout footer = homePage.getFooter();
        ContentLoggedout content = homePage.getContent();
        List<ConsoleMessage> consoleErrorMessages = homePage.getErrorMessages();
        Locator headerLogo = homePage.getHeader().LOGO;
        Locator headerMenuItems = header.MENU_ITEMS;

        logger.info("Reporting errors in the browser console if any.");
        if (! consoleErrorMessages.isEmpty()) {
            homePage.reportConsoleErrors(Level.WARN);
        }

        logger.info("Checking base page parameters.");
        softAssert.assertEquals(homePage.title(), pageTitle, "Wrong page title.");
        softAssert.assertEquals(pageWithResponse.getResponse().status(), HttpStatus.SC_OK, "Wrong response code.");
        softAssert.assertTrue(homePage.getErrorMessages().isEmpty(), "Page loaded with ");
        softAssert.assertEquals(homePage.url(), fullUrl, "Wrong page url.");

        logger.info("Checking header visibility.");
        softAssert.assertTrue(homePage.getHeader().HEADER_CONTAINER.isVisible(), "Header container is not visible");
        softAssert.assertTrue(headerLogo.isVisible(), "Logo is not visible");
        softAssert.assertTrue(headerLogo.isEnabled(), "Logo is not enabled");

        logger.info("Checking the list of header menu items.");
        headerMenuItems.all().forEach(menuItem -> {
            softAssert.assertTrue(menuItem.isVisible(), menuItem.innerText() + " header menu element is not visible.");
            softAssert.assertTrue(menuItem.isEnabled(), menuItem.innerText() + " header menu element is not enabled.");
        });
        Set<String> menuItems = new HashSet<>(Set.of("Product", "Way of Working", "Resources", "Partners", "Company", "Login", "Let's talk"));
        headerMenuItems.allInnerTexts()
                .forEach(menuItemText -> softAssert.assertTrue(menuItems.remove(menuItemText), menuItemText + " is not found in the required list of menu items."));
        softAssert.assertEquals(menuItems.size(), 0, menuItems.size() + " items from required menu items were not found in the header menu.");

        logger.info("Checking footer.");//TODO: Flaky for https://www.howwe.io/getting-started-with-howwe/
        softAssert.assertTrue(footer.FOOTER_CONTAINER.isVisible(), "Footer is not visible.");

        logger.info("Checking content.");
        softAssert.assertTrue(content.CONTENT_CONTAINER.isVisible(), "Content is not visible.");

        softAssert.assertAll();

        logger.info("Open page test END. Page is " + menuItemName + " - " + submenuItemName + ". URL = " + fullUrl);
    }
}
