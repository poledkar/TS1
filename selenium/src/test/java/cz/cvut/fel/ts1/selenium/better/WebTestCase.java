package cz.cvut.fel.ts1.selenium.better;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class WebTestCase {
    private WebDriver driver;

    @BeforeEach
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void clean() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
