package Praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import pages.DriverRule;
import pages.MainPage;

@RunWith(Parameterized.class)
public class QuestionsTest {
    private MainPage mainPage;
    private final String question;
    private final String expectedAnswer;
    private WebDriver driver;

    @Rule // Используем JUnit Rule для управления WebDriver
    public DriverRule driverRule = new DriverRule();


    public QuestionsTest(String question, String expectedAnswer) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "Проверка вопроса: {0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой." },
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    @Before
    public void setUp() {
        // Создаем экземпляр WebDriver через DriverRule
        WebDriver driver = driverRule.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage(); // Открываем главную страницу
    }

    @Test
    public void testQuestions() {

        mainPage.clickQuestion(question); // Нажимаем на вопрос

        String actualAnswer = mainPage.getAnswerText(question);


        assertEquals("Ответ на вопрос не совпадает: " + question, expectedAnswer, actualAnswer);
    }

}