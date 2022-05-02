package cz.cvut.fel.ts1.selenium;

import cz.cvut.fel.ts1.selenium.better.WebTestCase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoQATests extends WebTestCase {

    private WebDriver driver;

    @Test
    public void demoQAFormsEndToEndTest() {
        driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://demoqa.com/");
        WebElement formsDiv = driver.findElement(By.xpath("//h5[text()='Forms']//ancestor::div[@class='card mt-4 top-card']"));
        wait.until(ExpectedConditions.visibilityOf(formsDiv));
        jsCLick(formsDiv);
        WebElement practiceFormSpan = driver.findElement(By.xpath("//span[text()='Practice Form']"));
        wait.until(ExpectedConditions.visibilityOf(practiceFormSpan));
        practiceFormSpan.click();
    }

    public void jsCLick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    @Test
    public void demoQAPageObjectTest() {
        new DemoQAMainPage(getDriver())
                .clickForms()
                .clickPracticeForm();
    }
}
