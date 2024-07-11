package tests;

import helpers.CourierHelper;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.RestAssuredConfig;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTests extends RestAssuredConfig {

    private Courier courier;
    private int courierId;
    private final Random random = new Random();

    @Before
    public void setUp() {
        String login = "ninja" + random.nextInt(1000);
        courier = new Courier(login, "1234", "saske");
    }

    @Test
    @Description("Создание нового курьера")
    public void createCourier() {
        Response response = CourierHelper.createCourier(courier);
        response.then().statusCode(201).and().body("ok", equalTo(true));

        // Добавляем проверку, что поле id присутствует в ответе
        if (response.jsonPath().get("id") != null) {
            courierId = response.jsonPath().getInt("id");
        } else {
            System.out.println("Ошибка: поле 'id' отсутствует в ответе.");
        }
    }

    @Test
    @Description("Проверка ошибки при создании дублирующего курьера")
    public void createDuplicateCourier() {
        // Create the initial courier
        Response response = CourierHelper.createCourier(courier);
        response.then().statusCode(201).and().body("ok", equalTo(true));

        if (response.jsonPath().get("id") != null) {
            courierId = response.jsonPath().getInt("id");
        } else {
            System.out.println("Ошибка: поле 'id' отсутствует в ответе.");
        }

        // Attempt to create a duplicate courier
        Response duplicateResponse = CourierHelper.createCourier(courier);
        duplicateResponse.then().statusCode(409);
    }

    @Test
    @Description("Проверка ошибки при создании курьера без обязательного поля")
    public void createCourierWithoutRequiredField() {
        Courier invalidCourier = new Courier("", "1234", "saske");
        Response response = CourierHelper.createCourier(invalidCourier);
        response.then().statusCode(400);
    }

    @Test
    @Description("Авторизация курьера с корректными данными")
    public void loginCourier() {
        Response createResponse = CourierHelper.createCourier(courier);
        createResponse.then().statusCode(201).and().body("ok", equalTo(true));

        if (createResponse.jsonPath().get("id") != null) {
            courierId = createResponse.jsonPath().getInt("id");
        } else {
            System.out.println("Ошибка: поле 'id' отсутствует в ответе.");
        }

        Response loginResponse = CourierHelper.loginCourier(courier);
        loginResponse.then().statusCode(200).and().body("id", notNullValue());

        if (loginResponse.jsonPath().get("id") != null) {
            courierId = loginResponse.jsonPath().getInt("id");
        } else {
            System.out.println("Ошибка: поле 'id' отсутствует в ответе.");
        }
    }

    @Test
    @Description("Проверка ошибки при авторизации курьера без обязательного поля")
    public void loginCourierWithoutRequiredField() {
        Courier invalidCourier = new Courier("", "1234", "saske");
        Response response = CourierHelper.loginCourier(invalidCourier);
        response.then().statusCode(400);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            CourierHelper.deleteCourier(courierId);
        }
    }
}
