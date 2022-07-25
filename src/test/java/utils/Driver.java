package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.impl.SLF4JLog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static Logger LOG = LoggerFactory.getLogger(SLF4JLog.class);

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    @Parameters("browser")
    @BeforeClass
    public void setupTest(String browser) {
        switch (browser) {
            case "firefox" -> {
                try {
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setBrowserName(Browser.FIREFOX.browserName());
                    DRIVER.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
                } catch (MalformedURLException e) {
                    LOG.error(e.getMessage());
                }
            }
            case "chrome" -> {
                try {
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setBrowserName(Browser.CHROME.browserName());
                    DRIVER.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
                } catch (MalformedURLException e) {
                    LOG.error(e.getMessage());
                }
            }
            case "edge" -> {
                try {
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setBrowserName(Browser.EDGE.browserName());
                    DRIVER.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
                } catch (MalformedURLException e) {
                    LOG.error(e.getMessage());
                }
            }
            default -> throw new IllegalArgumentException("Browser type not supported for " + browser);
        }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        getDriver().manage().deleteAllCookies();
    }

//    @Parameters("browser")
//    @BeforeTest(alwaysRun = true)
//    public static WebDriver launchBrowser(String browser) throws MalformedURLException {
//        String Node = "http://localhost:4444/wd/hub";
//        DesiredCapabilities caps = new DesiredCapabilities();
//        switch (browser) {
//            case "chrome" -> {
//                caps.setBrowserName(Browser.CHROME.browserName());
//                caps.setVersion("101");
//                driver.set();
//                ChromeOptions chromeOptions = new ChromeOptions();
//                driver = new RemoteWebDriver(new URL(Node), chromeOptions);
//            }
//            case "firefox" -> {
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                driver = new RemoteWebDriver(new URL(Node), firefoxOptions);
//            }
//            case "edge" -> {
//                WebDriverManager.edgedriver().clearDriverCache().setup();
//                driver = new EdgeDriver();
//            }
//            default -> throw new IllegalArgumentException("Browser type not supported for " + browser);
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        logger("info", "Browser running!!!");
//        return driver;
//    }
//
//    @Parameters({"browser", "homePageUrl"})
//    @BeforeMethod(alwaysRun = true)
//    public static WebDriver launchBrowser(String browser, String homePageUrl) {
//        switch (browser) {
//            case "chrome" -> {
//                WebDriverManager.chromedriver().clearDriverCache().setup();
//                ChromeOptions options = new ChromeOptions();
//                //options.addArguments("--headless");
//                options.addArguments("--incognito");
//                driver = new ChromeDriver(options);
//                driver.manage().window().maximize();
//            }
//            case "firefox" -> {
//                WebDriverManager.firefoxdriver().clearDriverCache().setup();
//                driver = new FirefoxDriver();
//            }
//            case "edge" -> {
//                WebDriverManager.edgedriver().clearDriverCache().setup();
//                EdgeOptions options = new EdgeOptions();
//                options.addArguments("--incognito");
//                driver = new EdgeDriver(options);
//                driver.manage().window().maximize();
//            }
//            default -> throw new IllegalArgumentException("Browser type not supported for " + browser);
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        logger("info", "Browser running!!!");
//        return driver;
//    }


//    @BeforeMethod
//    public void goToUrl(String homePageUrl) {
//        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
//        getDriver().manage().deleteAllCookies();
//        getDriver().get(homePageUrl);
//    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        if (DRIVER != null) {
            getDriver().quit();
            DRIVER.remove();
        }
    }
}
