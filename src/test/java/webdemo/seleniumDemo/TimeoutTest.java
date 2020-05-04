package webdemo.seleniumDemo;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SeleniumExtension.class)
public class TimeoutTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testTitlePage() {
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
    }

    @Test
    public void testScriptTimeout() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
        assertThrows(ScriptTimeoutException.class, () -> js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 2000);"));
    }

    @Test
    public void testPageLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.MILLISECONDS);
        assertThrows(TimeoutException.class, () -> driver.navigate().to("https://worlds-highest-website.com"));

    }
}
