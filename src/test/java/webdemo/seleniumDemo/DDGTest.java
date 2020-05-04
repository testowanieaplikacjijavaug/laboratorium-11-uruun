package webdemo.seleniumDemo;

import io.github.bonigarcia.seljup.Arguments;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SeleniumExtension.class)
public class DDGTest {

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

    @Test
    public void testTitlePage() {
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
    }

    @Test
    public void testSource(){
        String source = driver.getPageSource();
        assertTrue(source.contains("DuckDuckGo"));
    }

    @Test
    public void testClick(@Arguments("--headless") FirefoxDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://duckduckgo.com/");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        assertTrue(driver.getCurrentUrl().contains("Koty"));
    }

    // inna metoda na klikniecie: submit() zamiast click()
    @Test
    public void testSubmit() throws InterruptedException {
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        assertEquals("Koty at DuckDuckGo", driver.getTitle());
    }

    // pierwszy i trzeci otrzymany wynik
    @Test
    public void testResult(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        driver.findElement(By.id("r1-0")).click(); // pierwszy
        String url1 = driver.getCurrentUrl();
        driver.navigate().back();
        driver.findElement(By.id("r1-2")).click(); // trzeci
        String url3 = driver.getCurrentUrl();
        assertNotEquals(url1, url3);
    }

    // pierwszy i trzeci otrzymany wynik (inaczej)
    @Test
    public void testResult_2() {
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        List<WebElement> list = driver.findElements(By.className("result__a"));
        String url1 = list.get(0).getText();
        String url3 = list.get(2).getText();
        assertNotEquals(url1, url3);
    }

    // gdy nie znajdzie dostajemy wyjatek
    @Test
    public void testNotExisting() {
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("not existing selector haha")));
    }

    // gdy findElements nie znajdzie daje pusta liste
    @Test
    public void testNotExistingElements() {
        assertTrue(driver.findElements(By.partialLinkText("not existing partialLinkText haha")).isEmpty());
    }
}
