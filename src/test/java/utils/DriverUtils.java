package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.impl.SLF4JLog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.HashMap;

public class DriverUtils extends Driver{
    private static Logger LOG = LoggerFactory.getLogger(SLF4JLog.class);

    public void clickWebElement(By by) {
        highlightElement(by);
        getDriver().findElement(by).click();
        waitForAjax();
    }

    public void type(By by, String text, boolean clear) {
        highlightElement(by);
        WebElement webElement = getDriver().findElement(by);
        if (clear) {
            webElement.clear();
        }
        webElement.sendKeys(text);
        waitForAjax();
    }

    public void selectByIndex(By by, int index) {
        highlightElement(by);
        Select oSel = new Select(getDriver().findElement(by));
        oSel.selectByIndex(index);
        waitForAjax();
    }

    public void waitForAjax() {
        JavascriptExecutor jsDriver = (JavascriptExecutor) getDriver();
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
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.background = 'yellow';", getDriver().findElement(by));
    }

    public static void logger(String logType, String message) {
        switch (logType) {
            case "error" -> {
                LOG.error(message);
            }
            case "warn" -> {
                LOG.warn(message);
            }
            default -> LOG.info(message);
        }
    }
}
