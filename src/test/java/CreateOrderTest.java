import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateOrderTest extends TestBase {


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create black order")
    @Description("Create black order")
    public void checkSuccessCreatingBlackOrderTest() {

        step("Создание заказа с черным цветом");

        File json;
        json = new File("src/main/resources/blackOrder");


        Boolean create =

                (Boolean) given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(201).extract()
                        .path("ok");

    }


    @Test
    @DisplayName("Create two colors order")
    @Description("Create two colors order")
    public void checkSuccessCreatingTwoColorsOrderTest() {

        step("Создание заказа с двумя цветами");

        File json;
        json = new File("src/main/resources/twoColorsOrder");


        Boolean create =

                (Boolean) given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract()
                        .path("ok");


    }

    @Test
    @DisplayName("Create no color order")
    @Description("Create no color order")
    public void checkSuccessCreatingNoColorOrderTest() {

        step("Создание заказа без цветов");

        File json = new File("src/main/resources/noColorOrder");


        Boolean create =

                (Boolean) given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract()
                        .path("ok");


    }

    @Test
    @DisplayName("Check track number order")
    @Description("Check track number order")
    public void checkTrackNumberOrderTest() {

        step("Проверка, что тело ответа содержит track");
        File json;
        json = new File("src/main/resources/blackOrder");


        Integer create =

                (Integer) given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract().body()
                        .path("track");
        System.out.println(create);

    }

}
