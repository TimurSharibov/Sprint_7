package tests;

import helpers.OrderHelper;
import models.Order;
import io.restassured.response.Response;
import org.junit.*;
import utils.RestAssuredConfig;
import io.qameta.allure.Step;

import java.util.Arrays;

import static org.hamcrest.Matchers.notNullValue;

public class OrderTests extends RestAssuredConfig {

    private Order order;

    @Before
    public void setUp() {
        order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList("BLACK"));
    }

    @Step("Создание заказа")
    @Test
    public void createOrder() {
        Response response = OrderHelper.createOrder(order);
        response.then().statusCode(201).and().body("track", notNullValue());
    }

    @Step("Получение списка заказов")
    @Test
    public void getOrderList() {
        Response response = OrderHelper.getOrderList();
        response.then().statusCode(200).and().body("orders", notNullValue());
    }
}
