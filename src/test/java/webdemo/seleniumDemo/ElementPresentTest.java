package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ElementPresentTest {

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
    public void setUp() throws Exception {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() throws Exception {
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
        WebDriverWait wait = new WebDriverWait(driver,5);
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.titleContains("Koty at DuckDuckGo"));
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
