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
    public void testSource(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.className("badge-link__title")), "Privacy, simplified."));
        String source = driver.getPageSource();
        assertTrue(source.contains("DuckDuckGo"));
    }

    @Test
    public void testClick() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("search_form_input"), "Koty"));
        assertTrue(driver.getCurrentUrl().contains("Koty"));
    }

    @Test
    public void testSubmit() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.titleContains("Koty at DuckDuckGo"));
        assertEquals("Koty at DuckDuckGo", driver.getTitle());
    }

    @Test
    public void testResult(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        driver.findElement(By.id("search_button_homepage")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("r1-0")));
        driver.findElement(By.id("r1-0")).click(); // pierwszy
        String url1 = driver.getCurrentUrl();
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("r1-0")));
        driver.findElement(By.id("r1-2")).click(); // trzeci
        String url3 = driver.getCurrentUrl();
        assertNotEquals(url1, url3);
    }

    @Test
    public void testResult_2() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("Koty");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("search_button_homepage")));
        driver.findElement(By.id("search_button_homepage")).click();
        List<WebElement> list = driver.findElements(By.className("result__a"));
        String url1 = list.get(0).getText();
        String url3 = list.get(2).getText();
        assertNotEquals(url1, url3);
    }

    @Test
    public void testNotExisting() {
        WebDriverWait wait = new WebDriverWait(driver,3);
        assertThrows(TimeoutException.class, () -> wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("not existing selector haha"))));
    }

    @Test
    public void testNotExistingElements() {
        assertTrue(driver.findElements(By.partialLinkText("not existing partialLinkText haha")).isEmpty());
    }
}
