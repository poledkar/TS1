package cz.cvut.fel.ts1.selenium.better;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DemoQATest extends WebTestCase {

    @Test
    public void openDemoQATest() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com");
    }

    @Test
    public void findElementsTest() {
        WebDriver driver = getDriver();
        driver.get("https://demoqa.com/automation-practice-form");
        WebElement firstName = driver.findElement(By.id("firstName"));
        driver.findElement(By.xpath("//input[@id='firstName']"));
        System.out.println();
    }

    @Test
    public void fillFormTest() {
        WebDriver driver = getDriver();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement lastNameInput = driver.findElement(By.xpath("//input[@id='lastName']"));
        WebElement userEmail = driver.findElement(By.xpath("//input[@id='userEmail']"));
        WebElement userNumber = driver.findElement(By.xpath("//input[@id='userNumber']"));
        WebElement sportsCheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']"));
        WebElement genderOtherRadio = driver.findElement(By.id("gender-radio-3"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));

        firstName.sendKeys("Karel");
        lastNameInput.sendKeys("Poledna");
        userEmail.sendKeys("poledkar@domain.invalid");
        userNumber.sendKeys("723327237");
//        sportsCheckbox.click(); // NEFUNGUJE
        executor.executeScript("arguments[0].click();", sportsCheckbox);
        executor.executeScript("arguments[0].click();", genderOtherRadio);
        currentAddress.sendKeys("Praha 11");
    }
}
