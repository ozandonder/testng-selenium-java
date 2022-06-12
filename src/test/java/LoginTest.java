import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Driver;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginTest extends Driver {

    LoginPage loginPage = new LoginPage();

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

    //ignore reason: different message is shown in each run
    @Test(description = "User should see warning message for invalid phone number")
    public void testLoginInvalidPhoneNumber() {
        loginPage.tryToLogin("9999999999")
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
