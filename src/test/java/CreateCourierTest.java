import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.LoginCourier;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest extends TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check creating courier")
    @Description("Checking creating courier")
    public void checkSuccessCreatingCourierTest() {

        //Проверка создания курьера
        step("Создание курьера");
        Courier courier = Courier.getRandom();

        Boolean create =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract()
                        .path("ok");
        assertTrue(create);

        //Проверка логина курьера и получение id

        step("Проверка логина курьера и получение id");
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        Integer idCourier =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier) // передача файла
                        .post("/api/v1/courier/login")
                        .then().extract().body().path("id");
        //Удаление курьера
        step("Удаление курьера");
        given()

                .delete("api/v1/courier/{idCourier}", idCourier) // отправка DELETE-запроса
                .then().assertThat().statusCode(200); // проверка, что сервер вернул код 200

    }

    @Test
    @DisplayName("Check not creating double courier")
    @Description("Checking not creating double courier")
    public void checkNotSuccessCreatingDoubleCourierTest() {

        //Проверка создания курьера
        File json;
        json = new File("src/main/resources/newCourier");


        Boolean create =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract()
                        .path("ok");
        assertTrue(create);

        step("Проверка повторного создания курьера");
        ValidatableResponse createTwo =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(409);


        LoginCourier loginCourier = new LoginCourier("Zt", "1234");
        Integer idCourier =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier) // передача файла
                        .post("/api/v1/courier/login")
                        .then().extract().body().path("id");
//        //Удаление курьера
        step("Удаление курьера");
        given()

                .delete("api/v1/courier/{idCourier}", idCourier) // отправка DELETE-запроса
                .then().assertThat().statusCode(200); // проверка, что сервер вернул код 200

    }

    @Test
    @DisplayName("Check not creating with wrong data")
    @Description("Checking not creating with wrong data")
    public void checkNotCreatingCourierWithWrongDataTest() {


        File json = new File("src/main/resources/wrongCourier");

        step("Создание курьера с неверными данными");
        ValidatableResponse create =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(400);

    }

    @Test
    @DisplayName("Check response")
    @Description("Check response")
    public void checkResponseTest() {

        //Проверка созжания курьера
        Courier courier = Courier.getRandom();


        Boolean create =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(201)
                        .extract()
                        .path("ok");
        assertTrue(create);

        step("Проверка ответа сервера");
        ValidatableResponse createTwo =

                given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(409);


        //Проверка логина курьера и получение id
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        Integer idCourier =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier) // передача файла
                        .post("/api/v1/courier/login")
                        .then().extract().body().path("id");
        //Удаление курьера
        step("Удаление курьера");
        given()

                .delete("api/v1/courier/{idCourier}", idCourier) // отправка DELETE-запроса
                .then().assertThat().statusCode(200); // проверка, что сервер вернул код 200

    }

}
