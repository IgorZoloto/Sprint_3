import io.qameta.allure.Allure;
import io.restassured.RestAssured;

public class TestBase {

    public void step(String info){
        Allure.step(info);
    }
}
