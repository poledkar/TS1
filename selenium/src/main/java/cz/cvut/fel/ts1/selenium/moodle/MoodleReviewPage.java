package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MoodleReviewPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//div[@class='othernav']//a[@class='mod_quiz-next-nav']")
    private WebElement finishReviewLink;

    public MoodleReviewPage(WebDriver driver) {
        super(driver);
    }

    public MoodleReviewPage waitForReview() {
        // Review sometimes takes a really long time to appear.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        wait.until(ExpectedConditions.elementToBeClickable(finishReviewLink));
        return this;
    }

    public MoodleQuizPage finishReview() {
        finishReviewLink.click();
        return new MoodleQuizPage(driver);
    }
}
