import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.Courier;
import model.LoginCourier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;

public class GetOrderListTest extends TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Check order list")
    @Description("Check order list")
    public void checkSuccessReturnOrderListTest() {

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

        //Проверка списка заказов
        step("Проверка списка заказов");
        Object orderList =
                given()

                        .body(idCourier)
                        .get("/api/v1/orders")
                        .then().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);


        //Удаление курьера
        step("Удаление курьера");

        given()

                .delete("api/v1/courier/{idCourier}", idCourier) // отправка DELETE-запроса
                .then().assertThat().statusCode(200); // проверка, что сервер вернул код 200

    }
}
