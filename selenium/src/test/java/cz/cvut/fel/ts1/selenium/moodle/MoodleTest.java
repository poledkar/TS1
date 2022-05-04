package cz.cvut.fel.ts1.selenium.moodle;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class MoodleTest {

    private static final String CONFIG_FILE = "moodle.properties";

    // Configuration options - please set here or in CONFIG_FILE. CONFIG_FILE takes precedence, if it exists.
    private static String chromeDriverPath = "c:\\Tools\\ChromeDriver.exe";
    private static String username = "poledkar";
    private static String password = ""; // censored
    private static LoginType loginType = LoginType.INTERNAL;
    private static String nameAndLabNumber = "Karel Poledna 107";

    private static final int SECONDS_IN_24_HOURS = 24 * 60 * 60;
    private static final String NOT_SOLAR_PLANET = "Oberon";
    private static final String EU_COUNTRY = "Rumunsko";

    private WebDriver driver;

    @BeforeAll
    public static void loadConfig() {
        Properties config = new Properties();
        try (
                FileReader fr = new FileReader(CONFIG_FILE);
                BufferedReader br = new BufferedReader(fr)
        ) {
            config.load(br);
            chromeDriverPath = config.getProperty("chromeDriverPath", chromeDriverPath);
            username = config.getProperty("username", username);
            password = config.getProperty("password", password);
            switch (config.getProperty("loginType", String.valueOf(loginType).toLowerCase(Locale.ROOT))) {
                case "sso": loginType = LoginType.SSO; break;
                case "internal": loginType = LoginType.INTERNAL; break;
                default: loginType = null;
            }
            nameAndLabNumber = config.getProperty("nameAndLabNumber", nameAndLabNumber);
        } catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }

    @BeforeEach
    public void init() {
        Assumptions.assumeFalse(chromeDriverPath.isBlank(),
                "Chrome driver path not set!");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @AfterEach
    public void clean() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void fillMoodleForm() {
        Assumptions.assumeFalse(username.isBlank(), "username not set!");
        Assumptions.assumeFalse(password.isBlank(), "password not set!");
        Assumptions.assumeFalse(loginType == null,
                "login type not set to either 'sso' or 'internal'");
        Assumptions.assumeFalse(nameAndLabNumber.isBlank(),
                "name and lab number not set!");

        new MainPage(driver)
                .clickLogin()
                .login(loginType, username, password)
                .clickTs1()
                .expandLabsSection()
                .clickRobotForm()
                .clickStartAttempt()
                .confirmStartAttempt()
                .fillNameAndLabNumber(nameAndLabNumber)
                .fillSecondsInDay(SECONDS_IN_24_HOURS)
                .fillPlanet(NOT_SOLAR_PLANET)
                .fillState(EU_COUNTRY)
                .endAttempt()
                .submitAll()
                .confirmSubmit()
                .finishReview()
                .openUserMenu()
                .logout()
                .continueLogout();
    }
}
