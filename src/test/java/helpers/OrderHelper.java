package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Order;
import static io.restassured.RestAssured.given;

public class OrderHelper {

    public static Response createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .post("/api/v1/orders");
    }

    public static Response getOrderList() {
        return given()
                .get("/api/v1/orders");
    }
}
