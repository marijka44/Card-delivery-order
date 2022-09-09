import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryOrderTest {
    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

    @Test
    void test1() {

        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");

        $("span[data-test-id='city'] input").sendKeys("Новосибирск");
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id=date] .input__control").sendKeys(generateDate(3));
        $("span[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("span[data-test-id='phone'] input").sendKeys("+79991234567");
        $("label[data-test-id='agreement']").click();
        $x("//*[text()='Забронировать']").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(3)));
    }
}
