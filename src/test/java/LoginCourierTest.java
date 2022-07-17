import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.LoginCourier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class LoginCourierTest extends TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check login courier")
    @Description("Checking login courier")
    public void checkSuccessLoginCourierTest() {

        step("Подготовка тестовых данных");
        //Проверка созжания курьера
        Courier courier = Courier.getRandom();
        // Response responseCreate = courierClient.createCourier(courier); //Создание курьера

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
    @DisplayName("Check wrong login courier")
    @Description("Checking wrong login courier")
    public void checkWrongLoginCourierTest() {

        //Проверка логина курьера и получение id
        step("Проверка логина курьера и получение id");

        LoginCourier loginCourier = new LoginCourier("test", "password");
        ValidatableResponse idCourier =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier) // передача файла
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(404);


    }

    @Test
    @DisplayName("Check no pass courier")
    @Description("Checking no pass courier")
    public void checkWrongNoPasswordCourierTest() {

        //Проверка логина курьера и получение id
        step("Проверка логина курьера и получение id");

        LoginCourier loginCourier = new LoginCourier("test", "");
        ValidatableResponse idCourier =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourier) // передача файла
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(400);


    }

    @Test
    @DisplayName("Check id courier")
    @Description("Checking id courier")
    public void checkSuccessIdCourierTest() {

        //Проверка создания курьера
        step("Создание курьера");
        Courier courier = Courier.getRandom();
        // Response responseCreate = courierClient.createCourier(courier); //Создание курьера

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
        System.out.println("Это ID курьера --------");
        System.out.println(idCourier);
        //Удаление курьера
        step("Удаление курьера");

        given()

                .delete("api/v1/courier/{idCourier}", idCourier) // отправка DELETE-запроса
                .then().assertThat().statusCode(200); // проверка, что сервер вернул код 200

    }


}
