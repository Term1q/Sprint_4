package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

public class MainPage {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;
    // маппинг локаторов вопросов FAQ
    private final Map<String, String> faqLocators = new HashMap<>();
    // Конструктор
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;

        // Инициализация маппинга вопросов и их локаторов
        faqLocators.put("Сколько это стоит? И как оплатить?", "accordion__panel-0");
        faqLocators.put("Хочу сразу несколько самокатов! Так можно?", "accordion__panel-1");
        faqLocators.put("Как рассчитывается время аренды?", "accordion__panel-2");
        faqLocators.put("Можно ли заказать самокат прямо на сегодня?", "accordion__panel-3");
        faqLocators.put("Можно ли продлить заказ или вернуть самокат раньше?", "accordion__panel-4");
        faqLocators.put("Вы привозите зарядку вместе с самокатом?", "accordion__panel-5");
        faqLocators.put("Можно ли отменить заказ?", "accordion__panel-6");
        faqLocators.put("Я жизу за МКАДом, привезёте?", "accordion__panel-7");
    }

    // Метод для открытия главной страницы
    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Метод для нажатия на вопрос в FAQ с ожиданием раскрытия ответа
    public void clickQuestion(String question) {
        String locatorAccordion = faqLocators.get(question);
        if (locatorAccordion == null) {
            throw new IllegalArgumentException("Локатор для вопроса не найден: " + question);
        }

        // Находим и кликаем по вопросу
        WebElement questionButton = driver.findElement(By.xpath("//div[contains(text(), '" + question + "')]"));
        scrollToElement(questionButton);
        jsExecutor.executeScript("arguments[0].click();", questionButton);

        // Ожидаем, пока ответ станет видимым
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorAccordion)));
    }

    // Метод для получения ответа на вопрос с увеличенным временем ожидания
    public String getAnswerText(String question) {
        String locatorAccordion = faqLocators.get(question);
        if (locatorAccordion == null) {
            throw new IllegalArgumentException("Локатор для вопроса не найден: " + question);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Увеличено время ожидания
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorAccordion)));

        return answerElement.getText();
    }

    // Метод для прокрутки до элемента аккордеона
    private void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Локатор кнопки "Заказать" в хедере
    private final By orderButtonHeader = By.className("Button_Button__ra12g");

    // Локатор второй кнопки "Заказать"

    private final By orderButtonUltraBig = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Метод для проверки активности кнопки "Заказать" в хедере
    public boolean isOrderButtonHeaderEnabled() {
        return driver.findElement(orderButtonHeader).isEnabled();
    }

    // Метод для проверки активности второй кнопки "Заказать" с перехватом исключения если объект не найден
    public boolean isOrderButtonUltraBigEnabled() {
        try {
            WebElement button = driver.findElement(orderButtonUltraBig); // Попытка найти элемент
            scrollToElement(button); // Прокрутить к элементу
            return button.isEnabled(); // Проверить, активна ли кнопка
        } catch (NoSuchElementException e) {
            System.err.println("Элемент не найден: " + orderButtonUltraBig);
            return false; // Возвращаем false, если элемент не найден
        }
    }

    // Метод нажатия на кнопку "Заказать" в хедере
    public void clickOrderButtonHeader() {
        WebElement button = driver.findElement(orderButtonHeader);
        button.click();
    }

    // Метод нажатия на вторую кнопку "Заказать"
    public void clickOrderButtonUltraBig() {
        WebElement button = driver.findElement(orderButtonUltraBig); // Найти элемент
        scrollToElement(button); // Прокрутить к элементу
        button.click(); // Нажать на кнопку
    }


}