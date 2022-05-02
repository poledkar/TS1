package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MoodleReviewPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//div[@class='othernav']//a[@class='mod_quiz-next-nav']")
    private WebElement finishReviewLink;

    public MoodleReviewPage(WebDriver driver) {
        super(driver);
    }

    public MoodleQuizPage finishReview() {
        finishReviewLink.click();
        return new MoodleQuizPage(driver);
    }
}
