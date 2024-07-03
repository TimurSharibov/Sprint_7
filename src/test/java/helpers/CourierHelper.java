package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Courier;
import static io.restassured.RestAssured.given;

public class CourierHelper {

    public static Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post("/api/v1/courier");
    }

    public static Response loginCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post("/api/v1/courier/login");
    }

    public static Response deleteCourier(int id) {
        return given()
                .delete("/api/v1/courier/" + id);
    }
}
