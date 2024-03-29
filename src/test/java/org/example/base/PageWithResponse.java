package org.example.base;

import com.microsoft.playwright.Response;
import lombok.Getter;
import org.example.page.BasePage;

@Getter
public class PageWithResponse {
    private final BasePage page;
    private final Response response;

    public PageWithResponse(BasePage page, Response response) {
        this.page = page;
        this.response = response;
    }
}
