package testcases;

import org.testng.annotations.*;
import pages.LoginPage;
import utils.Driver;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginTest extends Driver {

    private LoginPage loginPage;

    @Parameters("homePageUrl")
    @BeforeMethod
    public void testSetup (String homePageUrl) {
        getDriver().get(homePageUrl);
        loginPage = new LoginPage(getDriver());
    }

    @Test(description = "User should see warning message for empty phone number", groups = {"smoke"})
    public void testLoginEmptyPhoneNumber() {
        loginPage.tryToLogin("")
                .checkWarningMessageForPhoneNum();
    }

    @Test(description = "User should see warning message for missing phone number")
    public void testLoginMissingPhoneNumber() {
        loginPage.tryToLogin("53399999")
                .checkWarningMessageForPhoneNum();
    }

    @Test(description = "User should see warning message for invalid phone number", enabled = false)
    public void testLoginInvalidPhoneNumber() {
        loginPage.tryToLogin("5000000000")
                .checkErrorMessageForPhoneNum();
    }

    //ignore reason: have to use valid number
    @Test(description = "User should see error message for invalid otp code", enabled = false)
    public void testLoginInvalidOtpCode() {
        loginPage.tryToLogin("5555555555")
                .typeOtpCode("0000")
                .checkErrorMessageForOtpCode();
    }
}
