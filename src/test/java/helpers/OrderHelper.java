package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Order;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderHelper {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа {order}")
    public static Response createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .get(ORDERS_PATH);
    }
}
