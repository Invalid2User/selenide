import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackFormTest {

    private String dateWeNeed(int daysToAdd, String pattern){
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void EnteringValidData() {

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Барнаул");
        String data = dateWeNeed(4,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(data);
        $("[data-test-id='name'] input").setValue("Сталин Иосиф -Виссарионович");
        $("[data-test-id='phone'] input").setValue("+79879876543");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + data));
    }
}
