package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MoodleMyPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//h6[text()='B6B36TS1']")
    private WebElement b6b36ts1Link;

    public MoodleMyPage(WebDriver driver) {
        super(driver);
    }

    public MoodleTs1Page clickTs1() {
        b6b36ts1Link.click();
        return new MoodleTs1Page(driver);
    }
}
