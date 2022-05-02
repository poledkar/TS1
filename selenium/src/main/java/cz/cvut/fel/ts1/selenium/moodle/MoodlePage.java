package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public abstract class MoodlePage {

    private static final String USER_DROP_DOWN_ID = "action-menu-toggle-1";

    protected final WebDriver driver;

    @FindBy(how = How.ID, using = USER_DROP_DOWN_ID)
    private WebElement userDropDown;

    @FindBy(how = How.XPATH, using = "//a[@data-title='logout,moodle']/span")
    private WebElement logoutLink;

    protected MoodlePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public MoodlePage openUserMenu() {
        userDropDown.click();
        return this;
    }

    public MoodleLogoutPage logout() {
        logoutLink.click();
        return new MoodleLogoutPage(driver);
    }

    public boolean isLoggedIn() {
        return !driver.findElements(By.id(USER_DROP_DOWN_ID)).isEmpty();
    }
}
