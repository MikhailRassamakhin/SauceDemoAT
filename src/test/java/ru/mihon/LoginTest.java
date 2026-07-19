package ru.mihon;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import com.codeborne.selenide.WebDriverRunner;

public class LoginTest {

    @BeforeAll
    static void setup() {

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        Configuration.browserCapabilities.setCapability(
                "goog:chromeOptions",
                Map.of("prefs", prefs)
        );
        
        open("https://www.saucedemo.com/");
        $("#user-name").shouldBe(visible).setValue("standard_user");
        $("#password").setValue("secret_sauce");

        $("#login-button").click();
    }

    @Test
    void loginShouldBeSuccessful() {

        assertTrue(
                webdriver().driver().url().contains("inventory.html")
        );
    }

    @AfterAll
    public static void closeBrowser() {
        
        WebDriverRunner.closeWebDriver();
    }
}
