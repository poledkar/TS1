package cz.cvut.fel.ts1.selenium.moodle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MoodleTs1Page extends MoodlePage {

    @FindBy(how = How.XPATH, using = "//span[@class='sectionname'][text()='Cvičení']/parent::a")
    private WebElement labsSectionHeader;

    @FindBy(how = How.XPATH, using = "//span[@class='instancename'][text()='CV 11: Test pro Robota']")
    private WebElement formLink;

    public MoodleTs1Page(WebDriver driver) {
        super(driver);
    }

    public MoodleTs1Page expandLabsSection() {
        // Moodle seems to remember expanded/collapsed state of sections!
        if (labsSectionHeader.getAttribute("class").endsWith("collapsed")) {
            labsSectionHeader.click();
        }
        return this;
    }

    public MoodleQuizPage clickRobotForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500));
        wait.until(ExpectedConditions.elementToBeClickable(formLink)).click();
        return new MoodleQuizPage(driver);
    }
}
