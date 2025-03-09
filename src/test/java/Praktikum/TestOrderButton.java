package Praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.DriverRule;
import pages.MainPage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestOrderButton {

    private MainPage mainPage;

    @Rule // Используем JUnit Rule для управления WebDriver
    public DriverRule driverRule = new DriverRule();

    @Before
    public void setUp() {
        // Создаем экземпляр WebDriver через DriverRule
        WebDriver driver = driverRule.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage(); // Открываем главную страницу
    }

    @Test
    public void testHeaderOrderButton() {
        // Проверка активности кнопки "Заказать" в хедере
        assertTrue("Кнопка Заказать в хедере не активна или не найдена", mainPage.isOrderButtonHeaderEnabled());

        // Нажатие на кнопку "Заказать" в хедере
        mainPage.clickOrderButtonHeader();

        // Проверка перехода на нужный URL
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/order";
        String actualUrl = driverRule.getDriver().getCurrentUrl(); // Получаем URL через DriverRule
        System.out.println(actualUrl);
        assertEquals("Кнопка Заказать в хедере не ведет на " + expectedUrl, expectedUrl, actualUrl);
    }

    @Test
    public void testSecondOrderButton() {
        // Проверка активности второй кнопки "Заказать"
        assertTrue("Вторая кнопка 'Заказать' не активна или не найдена", mainPage.isOrderButtonUltraBigEnabled());

        // Нажатие на вторую кнопку "Заказать"
        mainPage.clickOrderButtonUltraBig();

        // Проверка перехода на нужный URL
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/order";
        String actualUrl = driverRule.getDriver().getCurrentUrl(); // Получаем URL через DriverRule
        assertEquals("Вторая кнопка 'Заказать' не ведет на " + expectedUrl, expectedUrl, actualUrl);
    }

}