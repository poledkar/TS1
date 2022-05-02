package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = "//a[@data-cy='login-sso-a']")
    private WebElement loginSsoLink;

    @FindBy(how = How.XPATH, using = "//label[@for='int-login']")
    private WebElement loginInternalLabel;

    @FindBy(how = How.ID, using = "username")
    private WebElement usernameField;

    @FindBy(how = How.ID, using = "password")
    private WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//button[@data-cy='login-int-button']")
    private WebElement loginInternalButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SsoLoginPage clickLoginSso() {
        loginSsoLink.click();
        return new SsoLoginPage(driver);
    }

    public LoginPage clickLoginInternal() {
        loginInternalLabel.click();
        return this;
    }

    public MoodleMyPage loginInternal(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginInternalButton.click();
        return new MoodleMyPage(driver);
    }

    public MoodleMyPage login(LoginType type, String username, String password) {
        MoodleMyPage page;
        switch (type) {
            case INTERNAL:
                page =  clickLoginInternal().loginInternal(username, password);
                break;
            case SSO:
                page = clickLoginSso().login(username, password);
                break;
            default:
                throw new InvalidArgumentException("unknown login type");
        }
        if (!page.isLoggedIn()) {
            throw new IllegalStateException("Login failed!");
        }
        return page;
    }
}
