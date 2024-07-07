package tests;

import helpers.OrderHelper;
import models.Order;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.RestAssuredConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderTests extends RestAssuredConfig {

    private Order order;
    private final String color;

    public OrderTests(String color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "{index}: Test with color={0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BLACK"}, {"GREY"}, {"BLACK,GREY"}, {""}
        });
    }

    @Before
    public void setUp() {
        order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", Arrays.asList(color.split(",")));
    }

    @Test
    @Description("Создание заказа с различными цветами")
    public void createOrder() {
        Response response = OrderHelper.createOrder(order);
        response.then().statusCode(201).and().body("track", notNullValue());
    }

    @Test
    @Description("Получение списка всех заказов")
    public void getOrderList() {
        Response response = OrderHelper.getOrderList();
        response.then().statusCode(200).and().body("orders", not(empty()));
    }
}
