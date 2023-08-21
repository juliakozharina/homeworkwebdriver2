import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HWTest {

    private static final Logger LOGGER = LogManager.getLogger(HWTest.class);
    private WebDriver driver;

    @BeforeAll
    public static void webDriverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void someTest1() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        String expected = "Онлайн‑курсы для профессионалов, дистанционное обучение";
        driver.get("https://duckduckgo.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ОТУС");
        searchBox.submit();

        WebElement firstResult = driver.findElement(By.xpath(
                "//article[@id=\"r1-0\"]"));
        String actual = firstResult.getText();
        LOGGER.info("Первый результат поиска: " + actual);

        assertThat(actual).contains(expected);
    }

    @Test
    public void someTest2() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        driver = new ChromeDriver(options);
        driver.get("https://otus.home.kartushin.su/lesson/index.htm");
        WebElement image = driver.findElement(By.xpath("(//a[@class=\"image-zoom\"])[1]"));
        image.click();

        WebElement modal = driver.findElement(By.xpath("//div[@class=\"pp_overlay\"]"));
        assertThat(modal.isDisplayed()).isTrue();
    }

    @Test
    public void someTest3() {
        driver = new ChromeDriver();

        driver.get("https://otus.ru");
        WebElement loginButton = driver.findElement(By.xpath("//button[@class=\"sc-mrx253-0 enxKCy sc-945rct-0 iOoJwQ\"]"));
        loginButton.click();

        WebElement usernameField = driver.findElement(By.xpath("(//input[@required])[1]"));
        usernameField.sendKeys("juliako13@mail.ru");

        WebElement passwordField = driver.findElement(By.xpath("(//input[@required])[2]"));
        passwordField.sendKeys("1234Qwerty12345&");

        WebElement submitButton = driver.findElement(By.xpath("(//div[@class=\"sc-9a4spb-2\"])[3]"));
        submitButton.click();

        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            LOGGER.info("Name: " + cookie.getName() + ", Value: " + cookie.getValue());
        }
    }

}
