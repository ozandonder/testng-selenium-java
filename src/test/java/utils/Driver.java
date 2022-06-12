package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.impl.SLF4JLog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Driver {
    public static WebDriver driver;
    private static Logger log = LoggerFactory.getLogger(SLF4JLog.class);


    @Parameters("browser")
    @BeforeTest(alwaysRun = true)
    public static WebDriver launchBrowser(String browser) {
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("--headless");
                options.addArguments("--incognito");
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().clearDriverCache().setup();
                driver = new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Browser type not supported for " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        logger("info", "Browser running!!!");
        return driver;
    }

    @Parameters("homePageUrl")
    @BeforeMethod(alwaysRun = true)
    public void goToUrl(String homePageUrl) {
        driver.manage().deleteAllCookies();
        driver.get(homePageUrl);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    public void clickWebElement(By by) {
        highlightElement(by);
        driver.findElement(by).click();
        waitForAjax();
    }

    public void type(By by, String text, boolean clear) {
        highlightElement(by);
        WebElement webElement = driver.findElement(by);
        if (clear) {
            webElement.clear();
        }
        webElement.sendKeys(text);
        waitForAjax();
    }

    public void selectByIndex(By by, int index) {
        highlightElement(by);
        Select oSel = new Select(driver.findElement(by));
        oSel.selectByIndex(index);
        waitForAjax();
    }

    public void waitForAjax() {
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Page has not loaded yet ");
            }
            boolean stillRunningAjax = (Boolean) jsDriver
                    .executeScript("return (window.jQuery != undefined && ($(':animated').length != 0 || jQuery.active != 0)) || document.readyState != \"complete\"");
            if (!stillRunningAjax) {
                break;
            }
        }
    }

    public void highlightElement(By by) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.background = 'yellow';", driver.findElement(by));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void logger(String logType, String message) {
        switch (logType) {
            case "error" -> {
                log.error(message);
            }
            case "warn" -> {
                log.warn(message);
            }
            default -> log.info(message);
        }
    }
}
