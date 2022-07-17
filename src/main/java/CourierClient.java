import io.restassured.response.Response;
import model.Courier;
import model.LoginCourier;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseApiClient {

    public Response createCourier(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier/");
    }

    public Response login(LoginCourier loginCourier) {
        return given()
                .spec(getReqSpec())
                .body(loginCourier)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Boolean deleteCourier(int courier) {
        return given()
                .spec(getReqSpec())
                .when()
                .delete(BASE_URL + "/api/v1/courier/"+ courier)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}
