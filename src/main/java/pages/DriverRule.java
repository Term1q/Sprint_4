package pages;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import ConfigPar.EnvConfig;

// Класс DriverRule расширяет ExternalResource, что позволяет использовать его как JUnit Rule.
// Это правило управляет жизненным циклом WebDriver: инициализирует его перед тестом и завершает работу после теста.
public class DriverRule extends ExternalResource {
    // WebDriver используется для управления браузером.
    private WebDriver driver;

    // Метод before() выполняется перед каждым тестом.
    @Override
    protected void before() throws Throwable {
        initDriver(); // Инициализация WebDriver.
    }

    // Метод after() выполняется после каждого теста.
    @Override
    protected void after() {
        System.out.println("Закрытие WebDriver...");
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                System.out.println("WebDriver закрыт.");
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии WebDriver: " + e.getMessage());
            }
        }
    }

    // Метод для инициализации WebDriver в зависимости от настроек.
    public void initDriver() throws Exception {
        // Проверка, какой браузер указан в системной переменной "browser".
        if ("firefox".equalsIgnoreCase(System.getProperty("browser"))) {
            startUpFirefox(); // Запуск Firefox, если указан "firefox".
        } else {
            startUpChrome(); // По умолчанию запускается Chrome.
        }
    }

    // Метод для получения экземпляра WebDriver.
    public WebDriver getDriver() {
        return driver;
    }

    // Метод для настройки и запуска ChromeDriver.
    public void startUpChrome() {
        // Настройка ChromeDriver с помощью WebDriverManager.
        WebDriverManager.chromedriver().setup();
        // Создание экземпляра ChromeDriver.
        driver = new ChromeDriver();
        // Установка неявного ожидания для всех операций поиска элементов.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }

    // Метод для настройки и запуска FirefoxDriver.
    public void startUpFirefox() {
        // Настройка FirefoxDriver с помощью WebDriverManager.
        WebDriverManager.firefoxdriver().setup();
        // Создание экземпляра FirefoxOptions для настройки Firefox.
        var opts = new FirefoxOptions()
                .configureFromEnv(); // Настройка опций из переменных окружения.
        // Создание экземпляра FirefoxDriver с указанными опциями.
        driver = new FirefoxDriver(opts);
        // Установка неявного ожидания для всех операций поиска элементов.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_WAIT));
    }
}