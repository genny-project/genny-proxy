package life.genny.googleapi;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
;
@QuarkusTest
public class BlackboxEndpointsTest {

    @Test
    public void getGoogleMapApi_passNoParameter_return200() {
        //https://maps.googleapis.com/maps/api/js?key=XXXXX&libraries=places,drawing
        given()
          .log().all()
          .when().get("/googleapi/v1/map")
          .then()
                .log().all()
             .statusCode(200)
             .body(
               containsString("google.maps.Load = function(apiLoad)")
             );
    }

    @Test
    public void getGoogleTimeZoneApi_passValidParameter_return200() {
        //http://localhost:8081/googleapi/v1/timezone?location=-37.913151%2C145.262253&timestamp=1458000000
        String response =  given()
                .log().all()
                .when()
                .param("location", "-37.913151,145.262253")
                .param("timestamp", 1458000000)
                .get("/googleapi/v1/timezone")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();
         assertEquals("Australia/Melbourne",response);


    }

}