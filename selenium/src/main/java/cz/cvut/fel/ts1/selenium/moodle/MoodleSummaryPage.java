package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MoodleSummaryPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//form[@action='https://moodle.fel.cvut.cz/mod/quiz/processattempt.php']//button[@type='submit']")
    private WebElement submitAllButton;

    @FindBy(how = How.XPATH, using = "//div[@class='confirmation-dialogue']//input[@class='btn btn-primary']")
    private WebElement confirmButton;

    public MoodleSummaryPage(WebDriver driver) {
        super(driver);
    }

    public MoodleSummaryPage submitAll() {
        submitAllButton.click();
        return this;
    }

    public MoodleReviewPage confirmSubmit() {
        confirmButton.click();
        return new MoodleReviewPage(driver);
    }
}
