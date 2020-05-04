package webdemo.seleniumDemo;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@ExtendWith(SeleniumExtension.class)
public class DDGHTMLUnitDriverTest {

    @Test
    public void testTitlePage(HtmlUnitDriver driver) {
        driver.get("https://duckduckgo.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testSource(HtmlUnitDriver driver){
        driver.get("https://duckduckgo.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String source = driver.getPageSource();
        assertTrue(source.contains("DuckDuckGo"));
        driver.quit();
    }
}
