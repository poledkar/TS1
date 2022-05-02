package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MoodleLogoutPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//section[@id='region-main']//button[@class='btn btn-primary']")
    private WebElement continueButton;

    public MoodleLogoutPage(WebDriver driver) {
        super(driver);
    }

    public void continueLogout() {
        continueButton.click();
        // Result presumably differ by LoginType.
        // For SSO it links to https://idp2.civ.cvut.cz/idp/logout.jsp
    }
}
