package Praktikum;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderPage;
import pages.RentPage;
import pages.PopUpPage;
import static org.junit.Assert.assertTrue;
import pages.DriverRule;
import org.junit.Rule;



// Указываем, что данный класс будет использовать параметризованные тесты
@RunWith(Parameterized.class)
public class TestOrder {
    private MainPage mainPage;
    private final String name; // Имя арендатора
    private final String surname; // Фамилия арендатора
    private final String address; // Адрес арендатора
    private final int stateMetroNumber; // Номер станции метро
    private final String telephoneNumber; // Телефон арендатора
    private final String date; // Дата аренды
    private final String duration; // Длительность аренды
    private final String colour; // Цвет самоката
    private final String comment; // Комментарий от пользователя
    private final String expectedHeader = "Заказ оформлен"; // Ожидаемый заголовок после оформления заказа

    @Rule // Используем JUnit Rule для управления WebDriver
    public DriverRule driverRule = new DriverRule();

    // Конструктор для параметров теста
    public TestOrder(String name, String surname, String address, int stateMetroNumber, String telephoneNumber,
                     String date, String duration, String colour, String comment) {
        this.name = name; // Устанавливаем имя
        this.surname = surname; // Устанавливаем фамилию
        this.address = address; // Устанавливаем адрес
        this.stateMetroNumber = stateMetroNumber; // Устанавливаем номер метро
        this.telephoneNumber = telephoneNumber; // Устанавливаем номер телефона
        this.date = date; // Устанавливаем дату
        this.duration = duration; // Устанавливаем длительность аренды
        this.colour = colour; // Устанавливаем цвет самоката
        this.comment = comment; // Устанавливаем комментарий
    }

    @Parameterized.Parameters(name = "Аренда на: {6}")
    public static Object[][] getParameters() {
        return new Object[][]{
                // Массив параметров для каждого тестового случая
                {"Виктор", "Петров", "ул Ленина 1", 100, "78005553535", "27.11.2024", "сутки", "GREY", "Позвоните"},
                {"Андрей", "Моржов", "ул Московская 2", 75, "79999999999", "21.01.2025", "двое суток", "BLACK", "Стучите два раза"},
                {"Анна", "Каренина", "ул Центральная 3", 10, "79087678945", "22.02.2025", "трое суток", "BLACK", "Пните дверь"},
                {"Иван", "Казаков", "ул Правая 1", 14, "79999999999", "27.02.2025", "четверо суток", "GREY", "Дверь открыта"},
                {"Виктория", "Журавлева", "ул Левая 2", 17, "79000000000", "19.01.2025", "пятеро суток", "BLACK", "Позвоните заранее"},
                {"Татьяна", "Пушкина", "ул Заводская 3", 12, "79555555555", "11.02.2025", "шестеро суток", "BLACK", "Позвоните в звонок"},
                {"Самуил", "Петровских", "ул Майская 3", 110, "79555555555", "31.12.2024", "семеро суток", "BLACK", "Настучите мелодию"},
        };
    }

    @Before
    public void setUp() {
        // Создаем экземпляр WebDriver через DriverRule
        WebDriver driver = driverRule.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage(); // Открываем главную страницу
    }

    @Test
    public void testScooterOrderOrder() {
        assertTrue("Кнопка Заказать в хедере не активна или не найдена", mainPage.isOrderButtonHeaderEnabled());
        mainPage.clickOrderButtonHeader();
        new OrderPage(driverRule.getDriver())
                .waitForLoadOrderPage()
                .inputName(name)
                .inputSurname(surname)
                .inputAddress(address)
                .changeStateMetro(stateMetroNumber)
                .inputTelephone(telephoneNumber)
                .clickNextButton();

        new RentPage(driverRule.getDriver())
                .waitAboutRentHeader()
                .inputDate(date)
                .inputDuration(duration)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        PopUpPage popUpPage = new PopUpPage(driverRule.getDriver());
        popUpPage.clickButtonYes();
        assertTrue(popUpPage.getHeaderAfterCreateOrder().contains(expectedHeader));
    }


}