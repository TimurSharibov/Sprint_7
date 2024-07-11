package utils;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class RestAssuredConfig {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = Config.BASE_URL;
    }
}
