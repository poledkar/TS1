package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MoodleSummaryPage extends MoodlePage {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") // False warning - Selenium updates it
    @FindBy(how = How.XPATH, using = "//form[@action='https://moodle.fel.cvut.cz/mod/quiz/processattempt.php']//button[@type='submit']")
    private List<WebElement> submitAllButton;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") // ditto
    @FindBy(how = How.XPATH, using = "//div[@class='confirmation-dialogue']//input[@class='btn btn-primary']")
    private List<WebElement> confirmButton;

    public MoodleSummaryPage(WebDriver driver) {
        super(driver);
    }

    public MoodleSummaryPage submitAll() {
        // Quiz sometimes goes out of time, skips summary page altogether and lands on review page.
        if (!submitAllButton.isEmpty()) {
            submitAllButton.forEach(WebElement::click);
            // Wait for confirm dialog to open
            for (WebElement button : confirmButton) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500));
                wait.until(ExpectedConditions.elementToBeClickable(button));
            }
        }
        return this;
    }

    public MoodleReviewPage confirmSubmit() {
        confirmButton.forEach(WebElement::click);
        // dialog closed, but next page not necessarily loaded yet
        return new MoodleReviewPage(driver).waitForReview();
    }
}
