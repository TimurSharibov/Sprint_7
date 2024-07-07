package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Courier;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierHelper {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = COURIER_PATH + "/login";

    @Step("Создание курьера {courier}")
    public static Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(COURIER_PATH);
    }

    @Step("Авторизация курьера {courier}")
    public static Response loginCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Удаление курьера с id {id}")
    public static Response deleteCourier(int id) {
        return given()
                .delete(COURIER_PATH + "/" + id);
    }
}
