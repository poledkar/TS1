package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = "//a[@data-cy='navbar-login-a']")
    private WebElement loginLink;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://moodle.fel.cvut.cz");
        PageFactory.initElements(driver, this);
    }

    public LoginPage clickLogin() {
        loginLink.click();
        return new LoginPage(driver);
    }
}
