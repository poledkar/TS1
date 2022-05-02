package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MoodleQuizPage extends MoodlePage {

    private static final String START_NEXT_ATTEMPT = "//div[@class='singlebutton quizstartbuttondiv']//button";

    @FindBy(how = How.XPATH, using = START_NEXT_ATTEMPT)
    private WebElement startNextAttemptButton;

    @FindBy(how = How.ID, using = "id_submitbutton")
    private WebElement startAttemptButton;

    public MoodleQuizPage(WebDriver driver) {
        super(driver);
    }

    public MoodleQuizPage clickStartAttempt() {
        if (driver.findElements(By.xpath(START_NEXT_ATTEMPT)).isEmpty()) {
            throw new IllegalStateException("Quiz is closed!");
        }
        startNextAttemptButton.click();
        return this;
    }

    public MoodleFormPage confirmStartAttempt() {
        startAttemptButton.click();
        return new MoodleFormPage(driver);
    }
}
