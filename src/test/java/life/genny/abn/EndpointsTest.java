package life.genny.abn;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;

@QuarkusTest
public class EndpointsTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/json?name=gada&size=1")
          .then()
             .statusCode(200)
             .body(instanceOf(AbnSearchResult.class));
    }

}