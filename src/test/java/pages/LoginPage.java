package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

import static org.testng.Assert.assertEquals;

public class LoginPage extends DriverUtils {
    private final WebDriver driver;

    public static final By LOGIN_OR_REGISTER_BUTTON = By.xpath("//*[@class='capsul']//*[@class='register-title']");
    public static final By PHONE_TEXTBOX = By.id("phone-input");
    public static final By SAVE_BUTTON = By.className("save-btn");
    public static final By WARNING_TEXT = By.className("warn-text");
    public static final By SEND_OTP_BUTTON = By.className("send-otp-button");
    public static final By OTP_WARNING_TEXT = By.className("otp-warn-text");
    public static final By OTP_CODE_TEXTBOX = By.className("otp-inp");
    public static final By SEARCH_TEXTBOX = By.xpath("//*[@class='v3-capsul']//*[@class='search-inp']");
    public static final By SEARCH_BUTTON = By.xpath("//*[@class='v3-capsul']//*[@alt='search shape icon']");

    public static final String PHONE_WARNING_MESSAGE = "Lütfen numarayı kontrol edin";
    public static final String PHONE_INVALID_MESSAGE = "Geçersiz telefon numarası.";
    public static final String OTP_INVALID_MESSAGE = "Hatalı doğrulama kodu girdin.";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage tryToLogin(String phoneNumber) {
        clickWebElement(LOGIN_OR_REGISTER_BUTTON);
        type(PHONE_TEXTBOX, phoneNumber, true);
        clickWebElement(SAVE_BUTTON);
        return this;
    }

    public LoginPage typeOtpCode(String otpCode) {
        type(OTP_CODE_TEXTBOX, otpCode, true);
        clickWebElement(SEND_OTP_BUTTON);
        return this;
    }

    public void searchProductWithName(String productName) {
        type(SEARCH_TEXTBOX, productName, true);
        clickWebElement(SEARCH_BUTTON);
    }

    public void checkWarningMessageForPhoneNum() {
        assertEquals(getDriver().findElement(WARNING_TEXT).getText(), PHONE_WARNING_MESSAGE);
    }

    public void checkErrorMessageForPhoneNum() {
        assertEquals(getDriver().findElement(WARNING_TEXT).getText(), PHONE_INVALID_MESSAGE);
    }

    public void checkErrorMessageForOtpCode() {
        assertEquals(getDriver().findElement(OTP_WARNING_TEXT).getText(), OTP_INVALID_MESSAGE);
    }
}
