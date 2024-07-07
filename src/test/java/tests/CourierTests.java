package tests;

import helpers.CourierHelper;
import models.Courier;
import io.restassured.response.Response;
import org.junit.*;
import utils.RestAssuredConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

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

    @Step("�������� �������")
    @Test
    @Description("�������� ������ �������")
    public void createCourier() {
        Response response = CourierHelper.createCourier(courier);
        response.then().statusCode(201).and().body("ok", equalTo(true));
    }

    @Step("�������� ������������ �������")
    @Test
    @Description("�������� ������ ��� �������� ������������ �������")
    public void createDuplicateCourier() {
        CourierHelper.createCourier(courier);
        Response response = CourierHelper.createCourier(courier);
        response.then().statusCode(409);
    }

    @Step("�������� ������� ��� ������������� ����")
    @Test
    @Description("�������� ������ ��� �������� ������� ��� ������������� ����")
    public void createCourierWithoutRequiredField() {
        Courier invalidCourier = new Courier("", "1234", "saske");
        Response response = CourierHelper.createCourier(invalidCourier);
        response.then().statusCode(400);
    }

    @Step("����������� �������")
    @Test
    @Description("����������� ������� � ����������� �������")
    public void loginCourier() {
        CourierHelper.createCourier(courier);
        Response response = CourierHelper.loginCourier(courier);
        response.then().statusCode(200).and().body("id", notNullValue());
        courierId = response.jsonPath().getInt("id");
    }

    @Step("����������� ������� ��� ������������� ����")
    @Test
    @Description("�������� ������ ��� ����������� ������� ��� ������������� ����")
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
