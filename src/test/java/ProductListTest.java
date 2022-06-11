import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductListPage;
import utils.Driver;

public class ProductListTest extends Driver {

    LoginPage loginPage = new LoginPage();
    ProductListPage productListPage = new ProductListPage();

    @Parameters("productName")
    @Test(description = "User should correctly see sorting for rising price", enabled = false)
    public void testRisingSorting(String productName) {
        loginPage.searchProductWithName(productName);
        productListPage.selectSortingOption("Fiyatı Artan")
                .checkRisingPrice();
    }

    @Parameters("productName")
    @Test(description = "User should be able to added the lowest price product to the cart", enabled = false)
    public void testAddToCartCheapestProduct(String productName) {
        loginPage.searchProductWithName(productName);
        productListPage.selectSortingOption("Fiyatı Artan")
                .addToCardFirstProduct()
                .checkBasketIconCount("1");
    }
}
