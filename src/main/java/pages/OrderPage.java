package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor; // Добавил переменную для выполнения JavaScript

    // Конструктор
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver; // Исправил: теперь создается jsExecutor для работы с JavaScript
    }

    // Метод для открытия главной страницы
    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    private final By orderHeader = By.className("Order_Header__BZXOb");
    private final By name = By.xpath(".//input[@placeholder='* Имя']");
    private final By surname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By stateMetro = By.className("select-search__input");
    private final By telephone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By buttonNext = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final String nameStateMetro = ".//button[@value='%s']"; // Форматная строка для выбора станции метро
    private final By yandexButton = By.xpath(".//*[@alt='Yandex']");
    private final By scooterButton = By.xpath(".//*[@alt='Scooter']");

    // Метод ожидания загрузки страницы заказа
    public OrderPage waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver ->
                driver.findElement(orderHeader).getText() != null &&
                        !driver.findElement(orderHeader).getText().isEmpty()
        );
        return this;
    }

    public OrderPage inputName(String newName) {
        driver.findElement(name).sendKeys(newName);
        return this;
    }

    public OrderPage inputSurname(String newSurname) {
        driver.findElement(surname).sendKeys(newSurname);
        return this;
    }

    public OrderPage inputAddress(String newAddress) {
        driver.findElement(address).sendKeys(newAddress);
        return this;
    }

    // Исправлено: параметр метода изменен с int на String, чтобы передавать название станции
    public OrderPage changeStateMetro(int stateName) {
        driver.findElement(stateMetro).click(); // Клик по полю выбора станции метро
        By newStateMetro = By.xpath(String.format(nameStateMetro, stateName)); // Формируем XPath для конкретной станции
        jsExecutor.executeScript("arguments[0].scrollIntoView();", driver.findElement(newStateMetro)); // Прокрутка к элементу
        driver.findElement(newStateMetro).click(); // Выбор станции метро
        return this;
    }

    public OrderPage inputTelephone(String newTelephone) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(telephone)); // Ожидание кликабельности поля
        driver.findElement(telephone).sendKeys(newTelephone);
        return this;
    }

    public void clickNextButton() {
        driver.findElement(buttonNext).click(); // Клик по кнопке "Далее"
    }

    public void clickYandex() {
        driver.findElement(yandexButton).click(); // Клик по кнопке "Яндекс"
    }

    public void clickScooter() {
        driver.findElement(scooterButton).click(); // Клик по кнопке "Скутер"
    }
}