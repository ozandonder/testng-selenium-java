package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductListPage extends DriverUtils {
    private final WebDriver driver;

    public static final By PRODUCT_LISTING_SORTING_DROPDOWN = By.className("ig-control-inp");
    public static final By PRODUCT_CARD = By.className("product-card");
    public static final By FIRST_PRODUCT_ADD_TO_CARD_BUTTON = By.xpath("//*[@class='product-card'][1]//*[@class='add-cap']");
    public static final By CART_BUTTON = By.xpath("//*[@class='sticky-top']//*[@class='count-notify']");

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductListPage selectSortingOption(String sortOption) {
        waitForAjax();
        int index;
        switch (sortOption) {
            case "Fiyatı Artan":
                index = 1;
                break;
            case "Fiyatı Azalan":
                index = 2;
                break;
            case "Alfabetik (A'dan Z'ye)":
                index = 3;
                break;
            case "İndirim Oranına Göre":
                index = 4;
                break;
            default:
                index = 0;
        }
        selectByIndex(PRODUCT_LISTING_SORTING_DROPDOWN, index);
        return this;
    }

    public void checkRisingPrice() {
        waitForAjax();
        int productCount = getDriver().findElements(PRODUCT_CARD).size();
        assertTrue(productCount > 1, "Not enough products for compare. Product size:" + productCount);
        double firstPrice = Double.parseDouble(getDriver().findElement(By.xpath("//*[@class='product-card'][1]//*[@class='discountless-price']"))
                .getText().split(" TL")[0].replace(",", "."));
        for (int i = 1; i <= 6; i++) {
            double secondPrice = Double.parseDouble(getDriver().findElement(By.xpath("//*[@class='product-card'][" + (i + 1) + "]//*[@class='discountless-price']")).getText()
                    .split(" TL")[0].replace(",", "."));
            assertTrue(firstPrice <= secondPrice, "");
            firstPrice = secondPrice;
        }
    }

    public ProductListPage addToCardFirstProduct() {
        clickWebElement(FIRST_PRODUCT_ADD_TO_CARD_BUTTON);
        return this;
    }

    public void checkBasketIconCount(String count) {
        assertEquals(getDriver().findElement(CART_BUTTON).getText(), count);
    }
}
