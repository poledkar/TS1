package cz.cvut.fel.ts1.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DemoQAMainPage {

    private final WebDriver driver;

    @FindBy(how = How.XPATH,
            using="//h5[text()='Forms']//ancestor::div[@class='card mt-4 top-card']")
    private WebElement formsDiv;

    public DemoQAMainPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://demoqa.com/");
        PageFactory.initElements(driver, this);
    }

    public FormsPage clickForms() {
        jsCLick(formsDiv);
        return new FormsPage(driver);
    }

    public void jsCLick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }
}
