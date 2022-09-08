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



    @Test
    void test1() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");

        LocalDate date = LocalDate.now().plusDays(3);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        String formattedDate = date.format(formatters);

        $("span[data-test-id='city'] input").sendKeys("Новосибирск");
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id=date] .input__control").sendKeys(formattedDate);
        $("span[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("span[data-test-id='phone'] input").sendKeys("+79991234567");
        $("label[data-test-id='agreement']").click();
        $x("//*[text()='Забронировать']").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + formattedDate));
    }
}
