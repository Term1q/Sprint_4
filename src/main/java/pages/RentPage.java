package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;



public class RentPage {
    WebDriver driver;
    private final By rentHeader = By.className("Order_Header__BZXOb");
    private final By date = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By durationRent = By.xpath(".//span[@class='Dropdown-arrow']");
    private final By colourBlack = By.id("black");
    private final By colourGrey = By.id("grey");
    private final By comment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By createOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By yandexButton = By.xpath(".//*[@alt='Yandex']");
    private final By scooterButton = By.xpath(".//*[@alt='Scooter']");

    public RentPage(WebDriver driver) {

        this.driver = driver;
    }

    //метод ожидания загрузки страницы
    public RentPage waitAboutRentHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(rentHeader).getText() != null
                && !driver.findElement(rentHeader).getText().isEmpty()
        ));
        return this;
    }

    public RentPage inputDate(String newDate) {
        driver.findElement(date).sendKeys(newDate);
        return this;
    }

    public RentPage inputDuration(String newDuration) {
        driver.findElement(durationRent).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.className("Dropdown-menu"))).click();
        return this;
    }

    public RentPage changeColour(String colour) {
        if (colour =="BLACK") {
            driver.findElement(colourBlack).click();
        } else if (colour == "GREY") {
            driver.findElement(colourGrey).click();
        }
        return this;
    }

    public RentPage inputComment(String newComment) {
        driver.findElement(comment).sendKeys(newComment);
        return this;
    }

    public void clickButtonCreateOrder() {

        driver.findElement(createOrderButton).click();
    }

    public void clickYandex() {

        driver.findElement(yandexButton).click();
    }
    public void clickScooter() {

        driver.findElement(scooterButton).click();
    }
}