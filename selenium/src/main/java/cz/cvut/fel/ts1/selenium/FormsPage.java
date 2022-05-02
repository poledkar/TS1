package cz.cvut.fel.ts1.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class FormsPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//span[text()='Practice Form']")
    private WebElement practiceFormSpan;

    public FormsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public FormsPage clickPracticeForm() {
        practiceFormSpan.click();
        return this;
    }
}
