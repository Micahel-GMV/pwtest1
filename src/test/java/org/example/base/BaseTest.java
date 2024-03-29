package org.example.base;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.PropertiesReader;
import org.example.page.BasePage;
import org.example.tools.ResourcesLoader;
import org.testng.annotations.*;

import java.lang.reflect.Type;
import java.util.List;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static Playwright playwright;
    protected Browser browser;

    @BeforeSuite
    public void launchPlaywright() {
        playwright = Playwright.create();
    }

    @BeforeMethod
    public void launchBrowser() {
        browser = playwright
                .chromium()
                .launch(new BrowserType
                        .LaunchOptions()
                        .setHeadless(Boolean.parseBoolean(PropertiesReader.getProperty("headless"))));
    }

    @AfterMethod
    public void closeBrowser() {
        browser.close();
    }

    @AfterSuite
    public void closePlaywright() {
        playwright.close();
    }

    public List<Cookie> getBrowserCookies() {
        return browser.contexts()
                .get(0)
                .cookies();
    }

    public void addCookie(Cookie cookie) {
        browser.contexts()
                .get(0)
                .addCookies(List.of(cookie));
    }

    public void setDefaultConsentCookie() {
        String resource = ResourcesLoader.loadResource("cookies");
        Gson gson = new Gson();
        Type cookieListType = new TypeToken<List<Cookie>>(){}.getType();
        List<Cookie> cookies = gson.fromJson(resource, cookieListType);
        addCookie(cookies.stream()
                .findFirst()
                .filter(cookie -> cookie.name.equalsIgnoreCase("cookieyes-consent"))
                .get());
    }

    protected <T extends BasePage> PageWithResponse openPage(String url, Class<T> pageClass) {
        logger.info("Opening page: " + url);
        Page page = browser.newPage();

        Response response = page.navigate(url);

        T pageObject;
        try {
            pageObject = pageClass.getConstructor(Page.class).newInstance(page);
        } catch (ReflectiveOperationException e) {
            logger.error("Error creating page object. Check you passed BasePage child.", e);
            throw new RuntimeException(e);
        }
        return new PageWithResponse(pageObject, response);
    }

    protected <T extends BasePage> PageWithResponse openPageConsented(String url, Class<T> pageClass) {
        PageWithResponse pageWithResponse = openPage(url, pageClass);
        setDefaultConsentCookie();
        return pageWithResponse;
    }
}
