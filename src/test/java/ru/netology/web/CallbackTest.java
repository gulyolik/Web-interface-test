package ru.netology.web;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CallbackTest {
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79967737496");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= order-success]")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckNameValidationV1(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys(" ");
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckNameValidationV2(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Vasiliy");
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckNameValidationV3(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Маша");
        driver.findElement(By.className("button")).click();
        String expected = "Укажите точно как в паспорте";
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);


    }

    @Test
    void shouldCheckPhoneNumberValidationV1(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys(" ");
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckPhoneNumberValidationV2(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+799677374966");
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckPhoneNumberValidationV3(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("phone");
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckPhoneNumberValidationV4(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("89967737496");
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void shouldCheckCheckBox(){
        driver.get("http:/localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79967737496");
        driver.findElement(By.className("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid .checkbox__text")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }

}
