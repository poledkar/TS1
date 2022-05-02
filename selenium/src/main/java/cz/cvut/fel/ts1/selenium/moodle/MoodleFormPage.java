package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class MoodleFormPage extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//div[@class='answer']/textarea")
    private WebElement fieldName;

    @FindBy(how = How.XPATH, using = "//span[@class='answer']/input[@type='text']")
    private WebElement fieldSeconds;

    @FindBy(how = How.XPATH, using = "//span[@class='qno'][text()='3']//ancestor::div[@class='info']//following-sibling::div[@class='content']//select")
    private WebElement selectPlanet;

    @FindBy(how = How.XPATH, using = "//span[@class='qno'][text()='4']//ancestor::div[@class='info']//following-sibling::div[@class='content']//select")
    private WebElement selectState;

    @FindBy(how = How.ID, using = "mod_quiz_next_nav")
    private WebElement endTestButton;

    public MoodleFormPage(WebDriver driver) {
        super(driver);
    }

    public MoodleFormPage fillNameAndLabNumber(String answer) {
        fieldName.sendKeys(answer);
        return this;
    }

    public MoodleFormPage fillSecondsInDay(int answer) {
        fieldSeconds.sendKeys(Integer.toString(answer));
        return this;
    }

    public MoodleFormPage fillPlanet(String answer) {
        new Select(selectPlanet).selectByVisibleText(answer);
        return this;
    }

    public MoodleFormPage fillState(String answer) {
        new Select(selectState).selectByVisibleText(answer);
        return this;
    }

    public MoodleSummaryPage endAttempt() {
        endTestButton.click();
        return new MoodleSummaryPage(driver);
    }
}
