package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ElementPresentPhantomJSTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.phantomjs().setup();
        driver = new PhantomJSDriver();
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

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Test
    public void testSubmit() {
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys(Keys.ENTER);
        assertTrue(isElementPresent(By.id("wedonttrack")));
    }

    @Test
    public void testNotExisting() {
        assertFalse(isElementPresent(By.cssSelector("not existing selector haha")));
    }

    @Test
    public void testResult(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        assertAll(
                () -> assertTrue(isElementPresent(By.id("r1-0"))),
                () -> assertTrue(isElementPresent(By.id("r1-2")))
        );
    }
}
